package com.dut.team92.issuesservice.domain;

import com.dut.team92.common.enums.WatcherType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "watcher")
@Getter
@Setter
@NoArgsConstructor
public class Watcher {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "watcher_id", unique = true, nullable = false)
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    private WatcherType watcherType;

    private UUID userId;
    private UUID issuesId;
}
