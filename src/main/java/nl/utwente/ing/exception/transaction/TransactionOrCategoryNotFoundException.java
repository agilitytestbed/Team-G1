package nl.utwente.ing.exception.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Transaction or category not found")
public class TransactionOrCategoryNotFoundException extends RuntimeException {
}
