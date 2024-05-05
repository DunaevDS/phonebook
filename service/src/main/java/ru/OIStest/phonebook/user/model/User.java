package ru.OIStest.phonebook.user.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // В дальнейшем в планах ФИО разбить на отедльные поля с фамилией, именем и отчеством.
    // Это позволит сохранять в БД по единому стандарту. Сейчас же можно писать как хочешь.
    @Column(nullable = false)
    private String name;

    private String notes;
}
