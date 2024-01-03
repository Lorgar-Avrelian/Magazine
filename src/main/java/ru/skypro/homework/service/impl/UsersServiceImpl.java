package ru.skypro.homework.service.impl;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.entity.Login;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.LoginRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;

import java.util.NoSuchElementException;

@Service
public class UsersServiceImpl implements UsersService {
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private static final Logger log = Logger.getLogger(UsersServiceImpl.class);

    public UsersServiceImpl(UserRepository userRepository, LoginRepository loginRepository) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
    }

    @Override
    public void setPassword(NewPassword newPassword) {
        User user = null;
        try {
            user = userRepository.findByEmail(getCurrentUsername()).get();
        } catch (NoSuchElementException e) {
            log.error(e.getMessage());
        }
        if (user != null) {
            Login login = loginRepository.findByUsername(user.getEmail());
            if (login.getPassword().equals(newPassword.getCurrentPassword())) {
                login.setPassword(newPassword.getNewPassword());
                loginRepository.save(login);
            }
        }

    }
    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
