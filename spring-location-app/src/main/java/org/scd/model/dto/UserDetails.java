package org.scd.model.dto;

import org.scd.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {


    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDetails(User user)
    {
        this.username = user.getEmail();
        this.password = user.getPassword();

        this.authorities = Arrays.stream(user.getRoles().toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public UserDetails()
    {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
