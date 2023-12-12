package ru.panteleevya.paste;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TmpTest {
    @Test
    void name() {
        String s = "2023-12-12T21:41:01.799159872Z";
        String actualLocalDateTime = Instant.parse(s).toString();
        assertEquals(s, actualLocalDateTime);
    }

    @Test
    void name2() {
        System.out.println(System.getProperty("some.key.with_strange-characters"));
    }
}
