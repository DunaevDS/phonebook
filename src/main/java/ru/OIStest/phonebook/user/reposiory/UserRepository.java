package ru.OIStest.phonebook.user.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import ru.OIStest.phonebook.phone.dto.PhoneDto;
import ru.OIStest.phonebook.phone.model.Phone;
import ru.OIStest.phonebook.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(List<Long> userIds, Pageable pageable);
}
