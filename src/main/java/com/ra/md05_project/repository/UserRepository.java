package com.ra.md05_project.repository;

import com.ra.md05_project.model.entity.ver1.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User getUserByUsername(String username);

    Page<User> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String searchTerm, String searchTerm1, Pageable pageable);

}
