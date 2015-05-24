package hue.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Brightness value was not between 1 and 254.")
public class InvalidBrightnessException extends RuntimeException {
}
