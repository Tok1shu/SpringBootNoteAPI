package net.tokishu.note.repo;

import net.tokishu.note.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByRole(String role);
}
