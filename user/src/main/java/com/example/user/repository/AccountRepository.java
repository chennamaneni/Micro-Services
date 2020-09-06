package com.example.user.repository;

import com.example.user.model.Account;
import com.example.user.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    @Modifying
    @Query("delete from Account a where a.id= ?1")
    void deleteUserAccountById(long accountId);

    @Query("select a from Account a where a.id= ?1")
    Account findByAccountId(long accountId);

}