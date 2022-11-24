package com.dut.team92.organizationservice.domain.entity;

import com.dut.team92.common.enums.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "project_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String description;
    private String domain;
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Project parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Project> children;

    @Column(columnDefinition = "BINARY(16)")
    private UUID organizationId;

    @Enumerated(EnumType.ORDINAL)
    private ProjectStatus projectStatus;
}
