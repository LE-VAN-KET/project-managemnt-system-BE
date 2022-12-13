package com.dut.team92.memberservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "members")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Members extends BaseDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "member_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

//    @Column(columnDefinition = "BINARY(16)")
//    private UUID userId;

    @Column(columnDefinition = "BINARY(16)")
    private UUID projectId;

    private String mailNotification;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MembersRoles membersRoles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private String firstName;
    @Transient
    private String lastName;
    @Transient
    private String displayName;
    @Transient
    private UUID userId;
    @Transient
    private String username;

    public Members(UUID id, UUID projectId, String mailNotification, String firstName, String lastName,
                   String displayName, UUID userId, String username) {
        this.id = id;
        this.projectId = projectId;
        this.mailNotification = mailNotification;
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.userId = userId;
        this.username = username;
    }
}
