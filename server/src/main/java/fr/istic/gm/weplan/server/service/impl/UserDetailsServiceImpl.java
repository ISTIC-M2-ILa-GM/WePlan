package fr.istic.gm.weplan.server.service.impl;

import fr.istic.gm.weplan.domain.model.entities.User;
import fr.istic.gm.weplan.domain.service.UserDaoService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.Collections.singleton;

/**
 * The user details service for security
 */
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDaoService userDaoService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDaoService.getUserDaoByEmail(email);
        Set<GrantedAuthority> authorities = singleton(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
