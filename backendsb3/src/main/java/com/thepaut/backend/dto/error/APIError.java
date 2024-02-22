package com.thepaut.backend.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class APIError {
    private String code;
    private String message;
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private Integer status;
    private List<APISubError> apiSubErrors;

    public APIError(String code, String message, String path, Integer status) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.apiSubErrors = new ArrayList<>();
    }

    public APIError(String code, String message, String path, Integer status, List<APISubError> apiSubErrors) {
        this.code = code;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.apiSubErrors = apiSubErrors;
    }
}
