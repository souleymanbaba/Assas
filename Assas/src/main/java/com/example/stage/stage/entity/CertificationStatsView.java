package com.example.stage.stage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certification_stats_view")
@Data
@NoArgsConstructor
public class CertificationStatsView {
    @Id
    private String id;



    @Column(name = "total_count")
    private Long totalCount;

    @Column(name = "certified_count")
    private Long certifiedCount;

    @Column(name = "pending_count")
    private Long pendingCount;

    @Column(name = "rejected_count")
    private Long rejectedCount;

    @Column(name = "certification_rate")
    private Double certificationRate;

    @Column(name = "avg_processing_time")
    private Double avgProcessingTime;
}
