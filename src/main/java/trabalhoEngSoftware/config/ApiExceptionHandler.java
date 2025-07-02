package trabalhoEngSoftware.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                                     HttpServletRequest request) {

        HttpStatus status = BAD_REQUEST;
        String mensagem = extrairErro(ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", mensagem);
        body.put("path", request.getServletPath());

        return new ResponseEntity<>(body, status);
    }

    private String extrairErro(MethodArgumentNotValidException ex) {

        Optional<ObjectError> erroOpt = ex.getBindingResult().getAllErrors().stream()
                .findFirst();

        if(erroOpt.isEmpty()){
            return "erro de validacao";
        }
        FieldError erro = (FieldError) erroOpt.get();

        return erro.getField() + " " + erro.getDefaultMessage();
    }
}
