package com.vano.config;

import com.vano.entity.BlogAuthor;
import com.vano.service.admin.BlogAuthorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    BlogAuthorServiceImpl blogAuthorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiem tra username co trong database hay khong.
        BlogAuthor blogAuthor = blogAuthorService.getByName(username);
        if (blogAuthor == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return User.builder()
                .username(blogAuthor.getUsername())
                .password(blogAuthor.getPassword())
                .roles(blogAuthor.getRole())
                .build();
    }
}
