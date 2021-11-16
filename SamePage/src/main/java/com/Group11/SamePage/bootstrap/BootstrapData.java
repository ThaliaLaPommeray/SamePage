package com.Group11.SamePage.bootstrap;
import com.Group11.SamePage.Users.User;
import com.Group11.SamePage.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    public BootstrapData(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Number of users:" + userRepository.count());
    }
}
