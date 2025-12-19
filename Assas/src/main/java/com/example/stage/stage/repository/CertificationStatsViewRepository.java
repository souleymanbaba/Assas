package com.example.stage.stage.repository;

import com.example.stage.stage.entity.CertificationStatsView;
import com.example.stage.stage.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationStatsViewRepository extends JpaRepository<CertificationStatsView, String> {


    @Query("SELECT csv FROM CertificationStatsView csv ORDER BY csv.certificationRate DESC")
    List<CertificationStatsView> findAllOrderByCertificationRate();
}
