package com.dut.team92.memberservice.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
public class Permissions {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "permission_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

//    @Column(columnDefinition = "BINARY(16)")
//    private UUID roleId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID functionId;

    private boolean enable;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roleId", referencedColumnName = "role_Id")
    private Roles role;
    @Transient
    private Function function;

    public Permissions(UUID id, UUID functionId, boolean enable, Function function) {
        this.id = id;
        this.functionId = functionId;
        this.enable = enable;
        this.function = function;
    }
}
