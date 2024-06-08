package com.electricBill.electricityBill.Repository;

import com.electricBill.electricityBill.Entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface represents the repository for the UserInfo entity.
 * It extends JpaRepository, which provides methods for CRUD operations.
 * The entity type is UserInfo and the ID type is String.
 * It includes methods to find a user by email and by ID.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInformation, Long>{

    /**
     * This method is used to find a user by their email.
     * It takes an email as input and returns a UserInfo object.
     *
     * @param email This is the email of the user to be found.
     * @return UserInfo This returns the UserInfo object if a user with the given email is found.
     */
    UserInformation findByEmail(String email);

    /**
     * This method is used to find a user by their ID.
     * It takes an ID as input and returns an Optional<UserInfo> object.
     * The Optional<UserInfo> object will contain a UserInfo object if a user with the given ID is found, and will be empty if no user is found.
     *
     * @param id This is the ID of the user to be found.
     * @return Optional<UserInfo> This returns an Optional<UserInfo> object.
     */
    Optional<UserInformation> findById(Long id);


}