package com.back_end.back_end.Entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class FixedExtensionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(columnDefinition = "varchar (20)")
    private String name;

    @ManyToMany(mappedBy = "FixedExtensions")
    private Set<UserEntity> users = new HashSet<>();

    @Builder
    public FixedExtensionEntity(String name
    ) {
        this.name = name;
    }
}
