package com.dut.team92.issuesservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
public class Attachments {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "attachment_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String fileName;
    private String description;
    private String link;

    @Column(name = "size")
    private BigDecimal size;

    private String contentType;
    private String containerType;
    private String containerId;

    private UUID authorId;
    private Boolean allowDownload;
    private Long downloadCount;
}
