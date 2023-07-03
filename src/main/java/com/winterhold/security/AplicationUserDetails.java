package com.winterhold.security;

import com.winterhold.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//seperti loginDTO
public class AplicationUserDetails implements UserDetails {

    private String username;
    private String password;

//    private List<GrantedAuthority> authorities = new ArrayList<>();

    public AplicationUserDetails(String username, String password) {
        this.username = username;
        this.password = password;
//        this.authorities = authorities;
    }

    public AplicationUserDetails(Account account) {
        this.username = account.getUserName();
        this.password = account.getPassword();
//        this.authorities.add(new SimpleGrantedAuthority(account.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
