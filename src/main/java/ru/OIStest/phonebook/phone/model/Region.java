package ru.OIStest.phonebook.phone.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public enum Region {
    TMN("072"),
    MSK("077"),
    TOM("70"),
    SPB("078");
    private final String value;
}
