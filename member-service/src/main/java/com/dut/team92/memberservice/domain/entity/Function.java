package com.dut.team92.memberservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import com.dut.team92.common.enums.Methods;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "functions")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Function extends BaseDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "function_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50)
    private String code;

    @Column(length = 50)
    private String name;

    private String description;

    @Column(length = 50)
    private String service;

    @Column(length = 50)
    private String controller;

    @Column(length = 50)
    private String action;

    @Column(length = 50)
    private String endpoint;

    private UUID dependonFunction;

    private Boolean isHidden;

    private Boolean isRequired;

    private Boolean isDelete;

    @Enumerated(EnumType.STRING)
    private Methods method;

    private UUID screenId;

//    @ManyToOne
//    @JoinColumn(name = "screenId", referencedColumnName = "screen_Id")
//    private Screens screen;
}
