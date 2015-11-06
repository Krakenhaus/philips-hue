package hue.dto;

import org.springframework.stereotype.Repository;

@Repository
public class PHNotifications {
    private BridgeStatus bridgeStatus;

    public enum BridgeStatus {
        CONNECTED("The bridge is operating normally."),
        CONNECTING("Trying to connect to the bridge..."),
        NOT_CONNECTED("Not connected to any bridge."),
        BRIDGE_NOT_RESPONDING("The bridge is not responding."),
        PUSHLINK_BUTTON_NOT_PRESSED("The pushlink button is not pressed."),
        PUSHLINK_AUTHENTICATION_FAILED("Pushlink authentication has failed"),
        BRIDGE_NOT_FOUND("Bridge not found."),
        UNKNOWN_ERROR("An unknown error has occured.");

        private String message;
        private BridgeStatus(String message) {
            this.message = message;
        }
    }

    public BridgeStatus getBridgeStatus() {
        return bridgeStatus;
    }

    public void setBridgeStatus(BridgeStatus bridgeStatus) {
        this.bridgeStatus = bridgeStatus;
    }
}
