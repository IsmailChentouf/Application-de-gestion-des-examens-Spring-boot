package com.ensah.projectIT.config;

import com.ensah.projectIT.models.Administrateur;
import com.ensah.projectIT.repositories.AdministrateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdministrateurRepository administrateurRepository;

    public CustomUserDetailsService(AdministrateurRepository administrateurRepository) {
        this.administrateurRepository = administrateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrateur admin = administrateurRepository.findByEmail(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                   .username(admin.getEmail())
                   .password(admin.getPassword())
                   .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                   .build();
    }
}
