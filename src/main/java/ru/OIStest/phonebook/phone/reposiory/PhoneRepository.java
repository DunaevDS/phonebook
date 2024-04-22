package ru.OIStest.phonebook.phone.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.OIStest.phonebook.phone.model.Phone;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
