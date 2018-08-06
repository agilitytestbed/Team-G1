package nl.utwente.ing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Limit must not be less than one!")
public class InvalidLimitException extends RuntimeException {
}
