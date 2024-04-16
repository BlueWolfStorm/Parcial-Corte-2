package _com6.Parcial.Corte2.repository;

import _com6.Parcial.Corte2.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("SELECT u FROM UserAccount u WHERE u.email = ?1")
    UserAccount findByEmail(String email);

}
