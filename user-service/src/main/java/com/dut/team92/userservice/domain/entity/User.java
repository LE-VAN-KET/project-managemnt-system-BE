package com.dut.team92.userservice.domain.entity;

import com.dut.team92.common.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "user_id", unique = true, nullable = false)
    private UUID id;

    @Column(length = 50)
    private String username;
    private String password;
    private Boolean isOrganizerAdmin;
    private Boolean isSystemAdmin;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Boolean isDelete;
    private String mailNotification;
    private UUID organizationId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserInformation userInformation;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Activity> activities;
}
