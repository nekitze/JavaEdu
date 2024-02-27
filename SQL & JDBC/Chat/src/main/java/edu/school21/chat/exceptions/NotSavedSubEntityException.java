package edu.school21.chat.exceptions;

import java.sql.SQLException;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException(SQLException e) {
        throw new RuntimeException(e);
    }

    public NotSavedSubEntityException(String text) {
        super(text);
    }
}
