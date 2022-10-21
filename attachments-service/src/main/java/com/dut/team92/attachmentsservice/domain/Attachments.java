package com.dut.team92.attachmentsservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @Column(name = "attachment_id", unique = true, nullable = false)
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
