package ru.OIStest.phonebook.user.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.OIStest.phonebook.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByPhones_Number(String number);
}
