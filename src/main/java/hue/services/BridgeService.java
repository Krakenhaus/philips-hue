package hue.services;

import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import hue.domain.HueSDK;
import hue.exceptions.bridge.BridgeNotFoundException;
import hue.exceptions.bridge.BridgeNotRespondingException;
import hue.exceptions.bridge.PushlinkAuthenticationFailedException;
import hue.exceptions.bridge.PushlinkButtonNotPressedException;
import org.springframework.beans.factory.annotation.Autowired;

public class BridgeService {

    @Autowired
    private HueSDK hueSDK;

    private PHBridge bridge;
    private PHBridgeResourcesCache cache;

    public BridgeService() {
        bridge = hueSDK.getSelectedBridge();
        cache = bridge.getResourceCache();
    }

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

    public PHBridge getBridge() {
        return bridge;
    }

    public PHBridgeResourcesCache getBridgeCache() {
        return cache;
    }
}
