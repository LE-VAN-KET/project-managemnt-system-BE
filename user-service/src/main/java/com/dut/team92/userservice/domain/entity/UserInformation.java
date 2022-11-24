package com.dut.team92.userservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import com.dut.team92.userservice.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_information")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class UserInformation extends BaseDomain {
    @Id
    @Column(name = "user_information_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 50)
    private String displayName;
    private Instant lastLogin;
    private Boolean mustChangePassword;
    private Instant passChangeAt;

    @MapsId
    @OneToOne(mappedBy = "userInformation", fetch = FetchType.LAZY)
    @JoinColumn(name = "user_information_id")
    private User user;
}
