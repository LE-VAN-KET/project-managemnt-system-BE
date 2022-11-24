package com.dut.team92.issuesservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import com.dut.team92.common.enums.Priority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
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
    @Column(name = "issues_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    private String name;

    private String description;

    @Column(columnDefinition = "BINARY(16)")
    private UUID projectId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID trackerId;
    private Calendar startDate;
    private Calendar dueDate;

    @Column(name = "estimated_hours")
    private BigDecimal estimatedHours;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issues_status_id")
    private IssuesStatus issuesStatus;

    @Column(columnDefinition = "BINARY(16)")
    private UUID authorId;
    private Integer doneRatio;

    @Column(columnDefinition = "BINARY(16)")
    private UUID tagId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID boardId;
    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = true)
    private Issues parent;

    @OneToMany(mappedBy = "parent")
    private Set<Issues> children;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "issues_type_id")
    private IssuesType issuesType;
}
