package technology.assessment.app.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JIDEX
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private String code;
    private T data;

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message, String code, T data) {
        this.message = message;
        this.data = data;
        this.code= code;
    }
}
