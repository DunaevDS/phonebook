package ru.OIStest.phonebook.phone.reposiory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.OIStest.phonebook.phone.model.Phone;


import java.util.List;


public interface PhoneRepository extends JpaRepository<Phone, Long> {

    List<Phone> findByIdIn(List<Long> ids, Pageable pageable);

    List<Phone> findByUser_IdIn(List<Long> ids, Pageable pageable);

    @Query(" select p from Phone p " +
            "where lower(p.number) like lower(concat('%', :search, '%')) " +
            "or lower(p.notes) like lower(concat('%', :search, '%')) ")
    Page<Phone> getPhonesBySearchQuery(@Param("search") String text, Pageable pageable);
}
