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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    @NotNull
    private String password;

    @ManyToMany
    @JoinTable(name = "user_fixedextension",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "fixedextension_id"))
    private Set<FixedExtensionEntity> FixedExtensions = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CustomExtensionEntity> customExtensions = new HashSet<>();




    @Builder
    public UserEntity(String userId, String password
    ) {
        this.userId = userId;
        this.password = password;
    }
}
