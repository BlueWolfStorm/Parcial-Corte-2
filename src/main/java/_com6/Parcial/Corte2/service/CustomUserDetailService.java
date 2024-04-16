package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserAccountRepository repository;

    @Autowired
    public CustomUserDetailService(UserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return mapToUserDetails(repository.findByEmail(username));
    }

    public UserDetails mapToUserDetails(UserAccount userAccount) {
       return User.builder()
                .username(userAccount.getEmail())
                .password(userAccount.getPassword())
                .build();
    }
}
