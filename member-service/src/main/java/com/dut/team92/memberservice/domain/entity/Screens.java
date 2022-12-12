package com.dut.team92.memberservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "screens", uniqueConstraints = {

})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Screens {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "screen_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(length = 50)
    private String code;

    private int groupId;

    @Column(length = 50)
    private String name;

    @Column(length = 255)
    private String description;

//
//    // list functions
//    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL)
//    private List<Function> functions;

}
