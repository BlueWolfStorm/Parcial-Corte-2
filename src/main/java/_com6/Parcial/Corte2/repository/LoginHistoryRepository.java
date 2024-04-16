package _com6.Parcial.Corte2.repository;

import _com6.Parcial.Corte2.model.LoginHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    @Query("SELECT l.loginDate FROM LoginHistory l WHERE l.userAccount.id = ?1")
    Page<LoginHistory> findByUserId(Long userId, Pageable pageable);
}
