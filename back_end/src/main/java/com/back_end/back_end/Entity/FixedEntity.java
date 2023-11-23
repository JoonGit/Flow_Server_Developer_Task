package com.back_end.back_end.Entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FixedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "varchar (20)")
    private String name;

    @ManyToMany(mappedBy = "FixedExtensions")
    private Set<UserEntity> users = new HashSet<>();

    @Builder
    public FixedEntity(String name
    ) {
        this.name = name;
    }
}
