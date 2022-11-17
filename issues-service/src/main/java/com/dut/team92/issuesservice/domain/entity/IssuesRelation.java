package com.dut.team92.issuesservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "issues_relation")
@Getter
@Setter
@NoArgsConstructor
public class IssuesRelation {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "issues_relation_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private Long relationType;
    private Long relationWith;
}
