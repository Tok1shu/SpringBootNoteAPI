package net.tokishu.note.repo;

import net.tokishu.note.model.User;
import net.tokishu.note.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByRole(UserRole role);

    @Modifying
    @Query("UPDATE User u SET u.username = :newUsername WHERE u.username = :oldUsername")
    void updateUsername(@Param("oldUsername") String oldUsername, @Param("newUsername") String newUsername);

}
