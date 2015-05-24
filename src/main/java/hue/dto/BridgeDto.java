package hue.dto;

import hue.domain.HueSDK;
import hue.exceptions.bridge.BridgeNotFoundException;
import hue.exceptions.bridge.BridgeNotRespondingException;
import hue.exceptions.bridge.PushlinkAuthenticationFailedException;
import hue.exceptions.bridge.PushlinkButtonNotPressedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BridgeDto {

    @Autowired
    private HueSDK hueSDK;

    public void checkForBridgeErrors() {

        if(getStatus() == HueSDK.BRIDGE_NOT_FOUND) {
            throw new BridgeNotFoundException();
        }

        if(getStatus() == HueSDK.BRIDGE_NOT_RESPONDING) {
            throw new BridgeNotRespondingException();
        }

        if(getStatus() == HueSDK.PUSHLINK_BUTTON_NOT_PRESSED) {
            throw new PushlinkButtonNotPressedException();
        }

        if(getStatus() == HueSDK.PUSHLINK_AUTHENTICATION_FAILED) {
            throw new PushlinkAuthenticationFailedException();
        }
    }

    public int getStatus() {
        return hueSDK.getStatus();
    }
}
