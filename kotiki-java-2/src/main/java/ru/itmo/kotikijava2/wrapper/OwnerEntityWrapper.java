package ru.itmo.kotikijava2.wrapper;

import lombok.Data;

import java.sql.Date;

@Data
public class OwnerEntityWrapper {
    private final int ownerId;
    private final String name;
    private final Date birthDay;
}
