package com.dut.team92.issuesservice.domain.entity;

import com.dut.team92.common.domain.converter.ColorConverter;
import com.dut.team92.common.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "issues_status")
@Getter
@Setter
@NoArgsConstructor
public class IssuesStatus {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "issues_status_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String description;

    @Column(name = "color", length = 7)
    @Convert(converter = ColorConverter.class)
    private Color color;

    @Column(columnDefinition = "BINARY(16)")
    private UUID organizationId;

    @OneToMany(mappedBy = "issuesStatus", fetch = FetchType.LAZY)
    private Set<Issues> issuesSet;
}
