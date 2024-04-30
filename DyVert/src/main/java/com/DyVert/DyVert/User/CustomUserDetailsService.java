package com.DyVert.DyVert.User;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.DyVert.DyVert.User.PasswordEncoderUtil;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String accountID) throws UsernameNotFoundException {

        User user = repo.getUserById(accountID);
        if (user == null) {
            throw new UsernameNotFoundException(accountID + " not found");
        }
        
        ArrayList<SimpleGrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(user.getAccountType()));
        
        String encodedPassword = PasswordEncoderUtil.encodePassword(user.getPassword());
        
        return new org.springframework.security.core.userdetails.User(
                user.getAccountID(), encodedPassword, authList);
    }

}

