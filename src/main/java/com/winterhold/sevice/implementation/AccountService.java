package com.winterhold.sevice.implementation;

import com.winterhold.dao.AccountRepository;
import com.winterhold.entity.Account;
import com.winterhold.security.AplicationUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isUsernameValid(String username){
        var existAcount = accountRepository.count(username);
        return (existAcount == 0);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = accountRepository.findById(username).get();
        return new AplicationUserDetails(entity);
    }

    public Account registerAccount(com.basilisk.Basilisk.dto.account.RegisterDTO dto){
        var encriptedPassword = passwordEncoder.encode(dto.getPassword());
        var entity = new Account(
                dto.getUserName(),
                encriptedPassword
        );
        var hasil = accountRepository.save(entity);
        return hasil;
    }
    
}

