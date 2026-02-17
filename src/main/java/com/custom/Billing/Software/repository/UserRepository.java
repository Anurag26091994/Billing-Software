package com.custom.Billing.Software.repository;

import com.custom.Billing.Software.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailId(String emailId);

    void deleteByEmailId(String emailId);

    Optional<Object> findByUserName(String userName);

}
