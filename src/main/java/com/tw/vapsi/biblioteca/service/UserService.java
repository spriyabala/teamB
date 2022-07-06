package com.tw.vapsi.biblioteca.service;

import com.tw.vapsi.biblioteca.exception.EmailAlreadyExistException;
import com.tw.vapsi.biblioteca.exception.ServiceException;
import com.tw.vapsi.biblioteca.model.User;
import com.tw.vapsi.biblioteca.repository.UserRepository;
import com.tw.vapsi.biblioteca.service.dto.UserDetailsDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;




import java.util.Optional;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(UserDetailsDTO::create)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user exists with username : %s", username)));
    }

    public User save(String firstName, String lastName, String email, String password) throws ServiceException {
       Optional<User> userObj = userRepository.findByEmail(email);

       if(userObj.isPresent()){
           throw new EmailAlreadyExistException();
       }

        String encodePassword = bCryptPasswordEncoder.encode(password);
        User user = new User(firstName, lastName, email, encodePassword);
        return userRepository.save(user);
    }





    public String fetchUserName()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName ;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;

    }




}
