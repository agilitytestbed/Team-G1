package nl.utwente.ing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Session ID is missing or invalid.")
public class InvalidSessionIdException extends RuntimeException{
}
