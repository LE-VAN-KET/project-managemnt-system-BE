package com.dut.team92.organizationservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Organization extends BaseDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "organization_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;
    private String description;
    private Boolean isDelete;

    private String logo;

    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;
}
