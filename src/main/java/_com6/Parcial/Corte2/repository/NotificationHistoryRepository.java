package _com6.Parcial.Corte2.repository;

import _com6.Parcial.Corte2.model.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    @Query("SELECT n.notificationDate FROM NotificationHistory n WHERE n.userAccount.id = ?1")
    Page<NotificationHistory> findByUserId(Long userId, Pageable pageable);
}
