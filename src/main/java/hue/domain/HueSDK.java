package hue.domain;

import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;

public class HueSDK {
    public static final int PENDING = -1;
    public static final int HEALTHY = 0;
    public static final int BRIDGE_NOT_RESPONDING = PHHueError.BRIDGE_NOT_RESPONDING;
    public static final int PUSHLINK_BUTTON_NOT_PRESSED = PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED;
    public static final int PUSHLINK_AUTHENTICATION_FAILED = PHMessageType.PUSHLINK_AUTHENTICATION_FAILED;
    public static final int BRIDGE_NOT_FOUND = PHMessageType.BRIDGE_NOT_FOUND;

    // PHHueSDK is a singleton
    private PHHueSDK phHueSDK;
    private int status = PENDING;


    public HueSDK() {
    }

    public PHBridge getSelectedBridge() {
        return phHueSDK.getSelectedBridge();
    }

    public PHHueSDK getPhHueSDK() {
        return phHueSDK;
    }

    public void setPhHueSDK(PHHueSDK phHueSDK) {
        this.phHueSDK = phHueSDK;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
