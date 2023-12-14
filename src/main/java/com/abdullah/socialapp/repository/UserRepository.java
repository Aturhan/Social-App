package com.abdullah.socialapp.repository;

import com.abdullah.socialapp.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel,String> {
    Optional<UserModel> findUserByUsername(String username);


}
