package com.store.book.model;

public enum BookType {

    AUTOBIOGRAPHY("AUTOBIOGRAPHY"),
    TECHNOLOGY("TECHNOLOGY"),
    NOVEL("NOVEL"),
    SCIENCE("SCIENCE"),
    POETRY("POETRY");

    private final String code;

    private BookType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
