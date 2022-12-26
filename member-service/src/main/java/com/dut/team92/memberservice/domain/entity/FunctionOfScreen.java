package com.dut.team92.memberservice.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "functionOfScreens", uniqueConstraints = {

})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FunctionOfScreen {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(columnDefinition = "BINARY(16)")
    private UUID functionId;
    @Column(columnDefinition = "BINARY(16)")
    private UUID screenId;

}
