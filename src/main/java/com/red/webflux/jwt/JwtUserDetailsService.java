package com.red.webflux.jwt;

import com.red.webflux.model.UserInfo;
import com.red.webflux.model.UserDTO;
import com.red.webflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @Author: Red
 * @Descpription:
 * @Date: 15:22 2019/8/19
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserInfo user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + s);
        }
        return new User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    /**
     * 保存
     * @param user
     * @return
     */
    public UserInfo save(UserDTO user) {
        UserInfo newUser = new UserInfo();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(newUser);
    }


}
