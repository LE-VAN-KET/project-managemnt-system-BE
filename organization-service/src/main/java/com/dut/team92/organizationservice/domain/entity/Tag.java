package com.dut.team92.organizationservice.domain.entity;

import com.dut.team92.common.domain.converter.ColorConverter;
import com.dut.team92.common.enums.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "tag_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID projectId;
    private String name;
    private String description;
    private int position;

    @Column(name = "color", length = 7)
    @Convert(converter = ColorConverter.class)
    private Color color;
}
