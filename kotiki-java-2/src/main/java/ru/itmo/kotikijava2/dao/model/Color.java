package ru.itmo.kotikijava2.dao.model;

public enum Color {
    Red("red"),
    Green("green"),
    Blue("blue"),
    Black("black");

    String text;

    private Color(String text) {
        this.text = text;
    }

    public static Color fromString(String s) {
        if (s != null) {
            for (Color color : Color.values()) {
                if (s.equalsIgnoreCase(color.text)) {
                    return color;
                }
            }
        }
        throw new IllegalArgumentException("No such color value");
    }
}
