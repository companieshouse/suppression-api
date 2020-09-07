//package uk.gov.companieshouse.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class SuppressionModelException extends RuntimeException {
//
//    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//
//        final Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error ->
//            errors.put(error.getField(), error.getDefaultMessage()));
//
//        return errors;
//    }
//}
