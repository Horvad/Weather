package org.example.server.dto;

public class CreatedResponseDTO {
    private String message;

    public CreatedResponseDTO(String message) {
        this.message = message;
    }

    public CreatedResponseDTO() {
        this.message = "Запрос выполнен успешно";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
