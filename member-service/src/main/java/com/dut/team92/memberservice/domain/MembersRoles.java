package com.dut.team92.memberservice.domain;

import com.dut.team92.common.domain.BaseDomain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "members_roles")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class MembersRoles extends BaseDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "member_role_id", unique = true, nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Members member;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private Roles role;
}
