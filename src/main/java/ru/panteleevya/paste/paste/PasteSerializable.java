package ru.panteleevya.paste.paste;

import java.io.Serializable;

public record PasteSerializable(String text) implements Serializable {
}
