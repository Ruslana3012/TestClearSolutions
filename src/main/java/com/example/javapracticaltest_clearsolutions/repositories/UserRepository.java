package com.example.javapracticaltest_clearsolutions.repositories;

import com.example.javapracticaltest_clearsolutions.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    List<User> findUsersByBirthDateBetween(Date from, Date to);

    void removeUserByEmail(String email);
}
