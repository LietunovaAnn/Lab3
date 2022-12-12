package org.example;

import lombok.Data;

import java.time.Instant;

@Data
public class Example {
    @Property
    private String stringProperty;

    @Property(name = "numberProperty")
    private int numberProperty;

    @Property(format = "dd.MM.yyyy tt:mm")
    private Instant timeProperty;
}
