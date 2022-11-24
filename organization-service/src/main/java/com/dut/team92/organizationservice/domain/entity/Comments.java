package com.dut.team92.organizationservice.domain.entity;

import com.dut.team92.common.domain.BaseDomain;
import com.dut.team92.common.enums.CommentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Comments extends BaseDomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "comment_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private String content;

    @Column(columnDefinition = "BINARY(16)")
    private UUID authorId;

    @Enumerated(EnumType.ORDINAL)
    private CommentType commentType;

    @Column(columnDefinition = "BINARY(16)")
    private UUID replyFor;

    @ManyToOne
    @JoinColumn(name = "comment_for", nullable = true)
    private Comments commentFor;

    @OneToMany(mappedBy = "commentFor")
    private Set<Comments> children;
}

