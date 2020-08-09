package com.example.user.repository;

import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;


//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   // List<User> findUserByUser_name(@Param("username") String name);

    @Query("select u from User u where u.user_name LIKE ?1")
    List<User> findUsersByUserName(String userName);

    @Transactional
    @Modifying
    @Query("delete from User u where u.user_name = ?1")
    void deleteUser(String userName);

    @Query("select u from User u where u.user_name = ?1")
    User findByUserName(String userName);

}
