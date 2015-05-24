package hue.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason =
        "RGB values should follow standard format - ([0, 255], [0, 255],  [0, 255]).")
public class InvalidRGBException extends RuntimeException {
}
