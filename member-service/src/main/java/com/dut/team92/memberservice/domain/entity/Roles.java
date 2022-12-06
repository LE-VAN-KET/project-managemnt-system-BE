package com.dut.team92.memberservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Roles extends BaseDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "BINARY(16)")
    private UUID organizationId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID assignable;

    @OneToMany(mappedBy = "role")
    private Set<MembersRoles> membersRoles;
}
