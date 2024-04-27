package ru.OIStest.phonebook.phone.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.OIStest.phonebook.phone.model.Phone;
import ru.OIStest.phonebook.user.model.User;

import java.util.List;


public interface PhoneRepository extends JpaRepository<Phone, Long> {
    List<User> findByNumber(String number);
}
