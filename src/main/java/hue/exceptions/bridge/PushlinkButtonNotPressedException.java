package hue.exceptions.bridge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Please press the pushlink button.")
public class PushlinkButtonNotPressedException extends RuntimeException {
}
