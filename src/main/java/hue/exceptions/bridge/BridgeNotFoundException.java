package hue.exceptions.bridge;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find a bridge.")
public class BridgeNotFoundException extends RuntimeException {
}
