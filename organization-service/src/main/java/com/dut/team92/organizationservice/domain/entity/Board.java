package com.dut.team92.organizationservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "board_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String description;
    private int position;

    @NotNull
    @Column(columnDefinition = "BINARY(16)")
    private UUID sprintId;
}
