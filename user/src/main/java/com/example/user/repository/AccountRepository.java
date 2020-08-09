package com.example.user.repository;

import com.example.user.model.Account;
import com.example.user.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

//@Repository
//@Transactional(readOnly = true)
//public interface AccountRepository extends JpaRepository<Account, Integer> {
//    @EntityGraph(attributePaths = "users")
//    List<Account> findFirst10ByOrderByNameAsc();
//
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Account b WHERE b.user.user_id = ?1")
//    void deleteInBulkByUserId(int userId);
//
//    @Transactional
//    void deleteByUserId(int categoryId);
//}


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // List<User> findUserByUser_name(@Param("username") String name);
   // List<Account> findByUserId(Long userId);
   // Optional<Account> findByIdAndUserId(Long id, Long userId);

}