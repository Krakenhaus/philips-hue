package hue.exceptions.bridge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The bridge is not responding.")
public class PushlinkAuthenticationFailedException extends RuntimeException {
}
