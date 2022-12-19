package com.dut.team92.userservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import com.dut.team92.common.enums.UserStatus;
import com.dut.team92.userservice.util.validator.ValidPassword;
import com.dut.team92.userservice.util.validator.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50)
    @ValidUsername
    private String username;

    @ValidPassword
    private String password;
    private Boolean isOrganizerAdmin = false;
    private Boolean isSystemAdmin = false;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Boolean isDelete = false;

    @Email
    private String mailNotification;

    @Column(columnDefinition = "BINARY(16)")
    private UUID organizationId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserInformation userInformation;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Activity> activities;

}
