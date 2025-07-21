package com.zerobase.minimart.user.repository;

import com.zerobase.minimart.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    boolean existsByPhoneNumberAndSellerYnTrue(String phoneNumber);

//    Optional<User> findByEmailAuthKey(String emailAuthKey);

}
