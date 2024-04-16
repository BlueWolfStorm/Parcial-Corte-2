package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.NotificationHistory;
import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.repository.NotificationHistoryRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationHistoryService {
    private final NotificationHistoryRepository notificationHistoryRepository;

    @Autowired
    public NotificationHistoryService(NotificationHistoryRepository notificationHistoryRepository) {
        this.notificationHistoryRepository = notificationHistoryRepository;
    }

    public Page<NotificationHistory> findAll(Pageable pageable) {
        return notificationHistoryRepository.findAll(pageable);
    }

    public NotificationHistory findById(Long id) {
        return notificationHistoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        notificationHistoryRepository.deleteById(id);
    }

    @Transactional
    public void send(UserAccount user) {
        NotificationHistory notification = new NotificationHistory(null, LocalDateTime.now(), user);
        notificationHistoryRepository.save(notification);
    }

    public Page<NotificationHistory> findByUserId(Pageable pageable, Long id) {
        return notificationHistoryRepository.findByUserId(id, pageable);
    }
}
