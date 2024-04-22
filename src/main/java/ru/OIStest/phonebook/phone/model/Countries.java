package ru.OIStest.phonebook.phone.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum Countries {
    RUS("RUS"),
    USA("USA"),
    FRA("FRA"),
    GER("GER");
    private final String value;
}
