package ru.OIStest.phonebook.user.reposiory;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.OIStest.phonebook.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(List<Long> userIds, Pageable pageable);

    @Query(" select u from User u " +
            "where lower(u.name) like lower(concat('%', :search, '%')) " +
            "or lower(u.notes) like lower(concat('%', :search, '%')) ")
    Page<User> getUsersBySearchQuery(@Param("search") String text, Pageable pageable);
}
