package com.example.subsavvy.Repository;

import com.example.subsavvy.Data.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByName(String name);

    User findByMail(String mail);

}
