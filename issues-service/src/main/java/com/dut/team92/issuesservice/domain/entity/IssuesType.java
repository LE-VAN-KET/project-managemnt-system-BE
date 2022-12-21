package com.dut.team92.issuesservice.domain.entity;

import com.dut.team92.common.domain.converter.ColorConverter;
import com.dut.team92.common.enums.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "issues_type")
@Getter
@Setter
@NoArgsConstructor
public class IssuesType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private String descriptions;
    private UUID organizationId;

    @Column(length = 7)
    @Convert(converter = ColorConverter.class)
    private Color color;

    private String urlIcon;

    @OneToMany(mappedBy = "issuesType", fetch = FetchType.LAZY)
    private Set<Issues> issuesSet;
}
