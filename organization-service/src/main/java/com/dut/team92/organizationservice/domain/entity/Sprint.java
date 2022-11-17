package com.dut.team92.organizationservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

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

    private UUID projectId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

    @Column(columnDefinition = "BINARY(16)")
    private UUID statusId;
    private Boolean isDelete;
    private int position;

}
