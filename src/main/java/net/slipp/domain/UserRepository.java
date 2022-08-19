package net.slipp.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    public default Users findOne(Long id) {
        return findById(id).orElse(null);
    }

    Users findByUserId(String userId);
}
