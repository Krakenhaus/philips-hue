package hue.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Could not access the BridgeDto file.")
public class HuePropertiesAccessException extends RuntimeException {
}
