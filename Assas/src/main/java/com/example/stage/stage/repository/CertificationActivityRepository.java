package com.example.stage.stage.repository;
import com.example.stage.stage.entity.CertificationActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CertificationActivityRepository extends JpaRepository<CertificationActivity, Long> {

    // Activités récentes
    Page<CertificationActivity> findByOrderByCreatedAtDesc(Pageable pageable);

    // Activités d'un professionnel
    List<CertificationActivity> findByProfessionalIdOrderByCreatedAtDesc(Long professionalId);

    // Activités d'un admin
    List<CertificationActivity> findByAdminIdOrderByCreatedAtDesc(Long adminId);

    // Activités par type d'action
    List<CertificationActivity> findByActionTypeOrderByCreatedAtDesc(String actionType);

    // Activités dans une période
    @Query("SELECT ca FROM CertificationActivity ca WHERE ca.createdAt BETWEEN :startDate AND :endDate " +
            "ORDER BY ca.createdAt DESC")
    List<CertificationActivity> findByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Statistiques d'activité par admin
    @Query("SELECT ca.adminId, ca.actionType, COUNT(ca) FROM CertificationActivity ca " +
            "WHERE ca.createdAt >= :startDate " +
            "GROUP BY ca.adminId, ca.actionType")
    List<Object[]> getAdminActivityStats(@Param("startDate") LocalDateTime startDate);

    // Nettoyage des anciennes activités
    @Query("DELETE FROM CertificationActivity ca WHERE ca.createdAt < :threshold")
    void deleteOldActivities(@Param("threshold") LocalDateTime threshold);
}
