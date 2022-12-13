package com.dut.team92.memberservice.domain.entity;

import com.dut.team92.common.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50)
    private String username;

    private Boolean isOrganizerAdmin = false;
    private Boolean isSystemAdmin = false;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    private Boolean isDelete = false;

    @Email
    private String mailNotification;

    @Column(columnDefinition = "BINARY(16)")
    private UUID organizationId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Members> members;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 50)
    private String displayName;
}
