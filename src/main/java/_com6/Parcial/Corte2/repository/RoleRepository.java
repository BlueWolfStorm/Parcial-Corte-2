package _com6.Parcial.Corte2.repository;

import _com6.Parcial.Corte2.model.Menu;
import _com6.Parcial.Corte2.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    @Query("SELECT DISTINCT r.menusAvailable FROM Role r WHERE r.id IN ?1")
    Page<Menu> findMenus(List<Long> rolIdList, Pageable pageable);

    @Query("SELECT ua.roleList FROM UserAccount ua WHERE ua.id = ?1")
    List<Role> findByUserId(Long aLong);

}
