package technology.assessment.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import technology.assessment.app.model.dto.response.ApiResponse;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import static technology.assessment.app.util.AppCode.*;
import static technology.assessment.app.util.MessageUtil.*;


@ControllerAdvice
@Slf4j
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatternSyntaxException.class)
    public ResponseEntity handlePatternSyntaxException(PatternSyntaxException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND, exception.getMessage()));
    }
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return  ResponseEntity.ok(new ApiResponse<>(FAILED, BAD_REQUEST, errors));
    }




    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity handleUnknownHostException(UnknownHostException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND, INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(ItemOutOfStockException.class)
    public ResponseEntity handleItemOutOfStockException(ItemOutOfStockException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, INSSUFICIENT, exception.getMessage()));
    }
    @ExceptionHandler(RecordAccessException.class)
    public ResponseEntity handleSecurityException(RecordAccessException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, UNAUTHORIZE_CODE, exception.getMessage()));
    }
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity handleRecordNotFoundExceptions(RecordNotFoundException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND, exception.getMessage()));
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleBadRequestExceptions(BadRequestException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, BAD_REQUEST, exception.getMessage()));

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegaException(RecordNotFoundException exception, WebRequest webRequest) {
        String requestUrl = webRequest.getContextPath();
        log.warn("Record not found for {} access through ", exception.getMessage(),requestUrl);
        return ResponseEntity.ok(new ApiResponse<>(FAILED, NOT_FOUND, exception.getMessage()));

    }
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        return  ResponseEntity.ok(new ApiResponse<>(FAILED, BAD_REQUEST+"", error)
        );
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handlerGlobalError(Exception exception) {
        exception.printStackTrace();
        log.warn("An error occur  {}", exception.fillInStackTrace());
        return ResponseEntity.ok(new ApiResponse<>(FAILED, ERROR_CODE, INTERNAL_SERVER_ERROR));
    }


}
