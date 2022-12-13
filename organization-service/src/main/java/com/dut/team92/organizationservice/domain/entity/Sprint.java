package com.dut.team92.organizationservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "sprint")
@Getter
@Setter
@NoArgsConstructor
public class Sprint {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "sprint_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID projectId;
    private String name;
    private String description;
    private Calendar startDate;
    private Calendar endDate;

    @Enumerated(EnumType.ORDINAL)
    private SprintStatus status;
    private Boolean isDelete;
    private int position;

    @Transient
    private List<Board> boardList = new ArrayList<>();
}
