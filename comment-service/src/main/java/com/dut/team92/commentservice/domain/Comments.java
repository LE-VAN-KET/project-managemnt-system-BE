package com.dut.team92.commentservice.domain;

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
    @Column(name = "comment_id", unique = true, nullable = false)
    private UUID id;

    private String content;
    private UUID authorId;

    @Enumerated(EnumType.ORDINAL)
    private CommentType commentType;

    private UUID replyFor;

    @ManyToOne
    @JoinColumn(name = "comment_for", nullable = true)
    private Comments commentFor;

    @OneToMany(mappedBy = "commentFor")
    private Set<Comments> children;
}
