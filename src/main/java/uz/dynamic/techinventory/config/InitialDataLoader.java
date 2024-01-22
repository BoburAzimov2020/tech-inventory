package uz.dynamic.techinventory.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.dynamic.techinventory.domain.Authority;
import uz.dynamic.techinventory.domain.User;
import uz.dynamic.techinventory.repository.AuthorityRepository;
import uz.dynamic.techinventory.repository.UserRepository;
import uz.dynamic.techinventory.security.AuthoritiesConstants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component("InitialDataLoader")
public class InitialDataLoader implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (authorityRepository.count() == 0) {
            authorityRepository.save(new Authority(AuthoritiesConstants.USER));
            authorityRepository.save(new Authority(AuthoritiesConstants.MODERATOR));
            authorityRepository.save(new Authority(AuthoritiesConstants.ADMIN));
        }

        // On StartUp add some users for managing.
        // 1. login: admin  password: admin [ADMIN, MODERATOR, USER]
        // 2. login: moder  password: moder [MODERATOR, USER]
        // 3. login: user  password: user   [USER]
        // 4. login: string  password: string [ADMIN, MODERATOR, USER]
        if (userRepository.count() == 0) {

            Set<Authority> adminAuthorities = new HashSet<>(authorityRepository.findAll());
            User adminUser = new User();
            adminUser.setLogin("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setEmail("admin@mail.ru");
            adminUser.setActivated(true);
            adminUser.setAuthorities(adminAuthorities);
            adminUser.setCreatedBy(Constants.SYSTEM);
            userRepository.save(adminUser);

            Set<Authority> moderAuthorities = new HashSet<>(
                    Arrays.asList(authorityRepository.findByName(AuthoritiesConstants.MODERATOR), authorityRepository.findByName(AuthoritiesConstants.USER)));
            User moderUser = new User();
            moderUser.setLogin("moder");
            moderUser.setPassword(passwordEncoder.encode("moder"));
            moderUser.setEmail("moder@mail.ru");
            moderUser.setActivated(true);
            moderUser.setAuthorities(moderAuthorities);
            moderUser.setCreatedBy(Constants.SYSTEM);
            userRepository.save(moderUser);

            Set<Authority> simpleUserAuthorities = new HashSet<>(
                    Arrays.asList(authorityRepository.findByName(AuthoritiesConstants.USER)));
            User simpleUser = new User();
            simpleUser.setLogin("user");
            simpleUser.setPassword(passwordEncoder.encode("user"));
            simpleUser.setEmail("user@mail.ru");
            simpleUser.setActivated(true);
            simpleUser.setAuthorities(simpleUserAuthorities);
            simpleUser.setCreatedBy(Constants.SYSTEM);
            userRepository.save(simpleUser);

            User stringUser = new User();
            stringUser.setLogin("string");
            stringUser.setPassword(passwordEncoder.encode("string"));
            stringUser.setEmail("string@mail.ru");
            stringUser.setActivated(true);
            stringUser.setAuthorities(adminAuthorities);
            stringUser.setCreatedBy(Constants.SYSTEM);
            userRepository.save(stringUser);
        }
    }
}
