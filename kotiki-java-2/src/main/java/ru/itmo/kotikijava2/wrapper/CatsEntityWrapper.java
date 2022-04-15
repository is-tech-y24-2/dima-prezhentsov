package ru.itmo.kotikijava2.wrapper;

import lombok.Data;

import java.sql.Date;

@Data
public class CatsEntityWrapper {
    private final String name;
    private final String breed;
    private final Date dateOfBirth;
    private final String color;
    private final int catId;
    private final int ownerId;
}
