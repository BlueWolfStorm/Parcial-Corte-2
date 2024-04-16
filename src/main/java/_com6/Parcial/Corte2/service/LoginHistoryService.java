package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.LoginHistory;
import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.repository.LoginHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoginHistoryService {
    private final LoginHistoryRepository loginHistoryRepository;

    @Autowired
    public LoginHistoryService(LoginHistoryRepository loginHistoryRepository){
        this.loginHistoryRepository = loginHistoryRepository;
    }

    public Page<LoginHistory> findAll(Pageable pageable){
        return loginHistoryRepository.findAll(pageable);
    }

    public LoginHistory findById(Long id){
        return loginHistoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id){
        loginHistoryRepository.deleteById(id);
    }

    @Transactional
    public void register(UserAccount user) {
        LoginHistory history = new LoginHistory(null, LocalDateTime.now(), user);
        loginHistoryRepository.save(history);
    }
}
