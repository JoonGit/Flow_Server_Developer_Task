package com.back_end.back_end.Seed;

import com.back_end.back_end.Entity.FixedEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.FixedRepository;
import com.back_end.back_end.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
        UserEntity seedUser = new UserEntity();
        seedUser.setUserId("user");
        seedUser.setPassword(encoder.encode("qwer1234"));
        userRepository.save(seedUser);




        String[] names = new String[]{ ".bat", ".cmd", ".com", ".cpl", ".exe", ".scr", ".js",};
        for (String name : names) {
            FixedEntity fixedEntity = new FixedEntity();
            fixedEntity.setName(name);
            fixedRepository.save(fixedEntity);
        }

    }
}
