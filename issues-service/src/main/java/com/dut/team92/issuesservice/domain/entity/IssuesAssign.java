package com.dut.team92.issuesservice.domain.entity;

import com.dut.team92.common.enums.IssuesAssignStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "issues_assign")
@Getter
@Setter
@NoArgsConstructor
public class IssuesAssign {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "issues_assign_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID memberId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID issuesId;

    @Enumerated(EnumType.ORDINAL)
    private IssuesAssignStatus status;
}
