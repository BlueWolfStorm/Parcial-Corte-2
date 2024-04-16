package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.GrantPermission;
import _com6.Parcial.Corte2.model.Role;
import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder){
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserAccount createUpdate(UserAccount entity){
        entity.setCreationDate(LocalDateTime.now());
        entity.setDisabled(false);

        if(entity.getPassword() != null)
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        return userAccountRepository.save(entity);
    }

    public Page<UserAccount> findAll(Pageable pageable){
        return userAccountRepository.findAll(pageable);
    }

    public UserAccount findById(Long id){
        return userAccountRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id){
        userAccountRepository.deleteById(id);
    }

    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email);
    }

    public UserAccount getMyProfile(Long id) {
        return userAccountRepository.findById(id).orElse(null);
    }

    public void grantPermission(GrantPermission grant) {
        UserAccount user = userAccountRepository.findById(grant.userId()).orElse(null);
        if(user != null){
            user.getRoleList().add(new Role(grant.roleId(), null, null, null));
            userAccountRepository.save(user);
        }
    }

    public void revokePermission(GrantPermission grant) {
        UserAccount user = userAccountRepository.findById(grant.userId()).orElse(null);
        if(user != null){
            user.getRoleList().removeIf(role -> role.getId().equals(grant.roleId()));
            userAccountRepository.save(user);
        }
    }
}
