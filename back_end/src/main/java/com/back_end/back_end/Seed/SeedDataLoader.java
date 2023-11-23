package com.back_end.back_end.Seed;

import com.back_end.back_end.Entity.Fixed.FixedEntity;
import com.back_end.back_end.Entity.User.UserEntity;
import com.back_end.back_end.Repository.Fixed.FixedRepository;
import com.back_end.back_end.Repository.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedDataLoader implements CommandLineRunner {

    private final UserRepository userRepository; // 데이터를 저장할 Repository 주입
    private final FixedRepository fixedRepository;
    private final BCryptPasswordEncoder encoder;

    public SeedDataLoader(UserRepository userRepository, FixedRepository fixedRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.fixedRepository = fixedRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // seed 유저
        Optional<UserEntity> optionalUser = userRepository.findByUserId("user");
        if (!optionalUser.isPresent()) {
            UserEntity seedUser = new UserEntity();
            seedUser.setUserId("user");
            seedUser.setPassword(encoder.encode("qwer1234"));
            userRepository.save(seedUser);
        }

        // seed Fixed 값
        String[] names = new String[]{".bat", ".cmd", ".com", ".cpl", ".exe", ".scr", ".js",};
        for (String name : names) {
            Optional<FixedEntity> optionalFixed = fixedRepository.findByName(name);
            if (!optionalFixed.isPresent()) {
                FixedEntity fixedEntity = new FixedEntity();
                fixedEntity.setName(name);
                fixedRepository.save(fixedEntity);
            }
        }
    }
}
