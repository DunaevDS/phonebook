package ru.OIStest.phonebook.phone.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.OIStest.phonebook.phone.model.Phone;


public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
