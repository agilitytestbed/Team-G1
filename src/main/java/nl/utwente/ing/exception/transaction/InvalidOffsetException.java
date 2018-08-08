package nl.utwente.ing.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Offset index must not be less than zero!")
public class InvalidOffsetException extends RuntimeException {
}
