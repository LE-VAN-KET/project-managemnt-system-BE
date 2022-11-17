package com.dut.team92.organizationservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tracker")
@Getter
@Setter
@NoArgsConstructor
public class Tracker {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "tracker_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    private String name;
    private String description;

    private UUID organizationId;
}
