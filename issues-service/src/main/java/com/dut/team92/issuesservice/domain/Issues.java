package com.dut.team92.issuesservice.domain;

import com.dut.team92.common.domain.BaseDomain;
import com.dut.team92.common.enums.Priority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "issues")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Issues extends BaseDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "issues_id", unique = true, nullable = false)
    private UUID id;

    private String name;

    private String description;

    private UUID projectId;
    private UUID trackerId;
    private Instant startDate;
    private Instant dueDate;

    @Column(name = "estimated_hours")
    private BigDecimal estimatedHours;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issues_status_id")
    private IssuesStatus issuesStatus;

    private UUID authorId;
    private Integer doneRatio;

    private UUID tagId;
    private UUID boardId;
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Issues parent;

    @OneToMany(mappedBy = "parent")
    private Set<Issues> children;
}
