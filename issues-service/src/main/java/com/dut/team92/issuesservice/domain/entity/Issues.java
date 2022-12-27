package com.dut.team92.issuesservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import com.dut.team92.common.enums.Priority;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Issues extends BaseDomain {
    @Id
    @Column(name = "issues_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @NotNull
    private String name;

    private String issuesKey;

    private String description;

    @Column(columnDefinition = "BINARY(16)")
    private UUID projectId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID trackerId;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startDate;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dueDate;

    @Column(name = "estimated_hours")
    private BigDecimal estimatedHours;

    @Enumerated(EnumType.ORDINAL)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Issues parent;

    @OneToMany(mappedBy = "parent")
    private Set<Issues> children;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "issues_type_id")
    private IssuesType issuesType;

    private int position;

    private transient UUID assignMemberId;

    public Issues(UUID id, String name, String issuesKey, UUID projectId, Priority priority, IssuesStatus issuesStatus, UUID authorId, UUID boardId, Boolean isPublic, IssuesType issuesType, int position,  UUID assignMemberId) {
        this.id = id;
        this.name = name;
        this.issuesKey = issuesKey;
        this.projectId = projectId;
        this.priority = priority;
        this.issuesStatus = issuesStatus;
        this.authorId = authorId;
        this.boardId = boardId;
        this.isPublic = isPublic;
        this.issuesType = issuesType;
        this.position = position;
        this.assignMemberId = assignMemberId;
    }

    public Issues(UUID id, String name, String issuesKey, UUID projectId, Priority priority, IssuesStatus issuesStatus, UUID authorId, UUID boardId, Boolean isPublic, IssuesType issuesType, int position,  UUID assignMemberId, Calendar startDate, Calendar dueDate) {
        this.id = id;
        this.name = name;
        this.issuesKey = issuesKey;
        this.projectId = projectId;
        this.priority = priority;
        this.issuesStatus = issuesStatus;
        this.authorId = authorId;
        this.boardId = boardId;
        this.isPublic = isPublic;
        this.issuesType = issuesType;
        this.position = position;
        this.assignMemberId = assignMemberId;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public Issues(UUID id, String name, String issuesKey, String description, UUID projectId, UUID trackerId, Calendar startDate, Calendar dueDate, BigDecimal estimatedHours, Priority priority, IssuesStatus issuesStatus, UUID authorId, Integer doneRatio, UUID tagId, UUID boardId, Boolean isPublic, IssuesType issuesType, int position, UUID assignMemberId) {
        this.id = id;
        this.name = name;
        this.issuesKey = issuesKey;
        this.description = description;
        this.projectId = projectId;
        this.trackerId = trackerId;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimatedHours = estimatedHours;
        this.priority = priority;
        this.issuesStatus = issuesStatus;
        this.authorId = authorId;
        this.doneRatio = doneRatio;
        this.tagId = tagId;
        this.boardId = boardId;
        this.isPublic = isPublic;
        this.issuesType = issuesType;
        this.position = position;
        this.assignMemberId = assignMemberId;
    }
}
