package hue.services;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import hue.dto.PHNotifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class PHBridgeService {

    @Autowired
    PHNotifications notifications;

    private PHHueSDK phHueSDK;
    private String username;

    @PostConstruct
    public void initPHHueSDK() {
        phHueSDK = PHHueSDK.create();
        phHueSDK.getNotificationManager().registerSDKListener(getListener());
        notifications.setBridgeStatus(PHNotifications.BridgeStatus.NOT_CONNECTED);
    }

    public boolean connectToAccessPoint(String username, String ip) {
        this.username = username;
        notifications.setBridgeStatus(PHNotifications.BridgeStatus.CONNECTING);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
        return true;
    }

    public void findBridges() {
        phHueSDK = PHHueSDK.getInstance();
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
    }

    private PHSDKListener listener = new PHSDKListener() {
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {
            // TODO: verify which access point we're trying to connect to
            accessPointsList.get(0).setUsername(username);
            phHueSDK.connect(accessPointsList.get(0));
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            phHueSDK.startPushlinkAuthentication(accessPoint);
            System.out.println("auth_required");
        }

        @Override
        public void onBridgeConnected(PHBridge bridge) {
            phHueSDK.setSelectedBridge(bridge);
            phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
            notifications.setBridgeStatus(PHNotifications.BridgeStatus.CONNECTED);
        }

        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge arg1) {
        }

        @Override
        public void onConnectionLost(PHAccessPoint arg0) {
            System.out.println("connection lost");
        }

        @Override
        public void onConnectionResumed(PHBridge arg0) {
        }

        @Override
        public void onError(int code, final String message) {
            switch (code) {
                case PHHueError.BRIDGE_NOT_RESPONDING:
                    notifications.setBridgeStatus(PHNotifications.BridgeStatus.BRIDGE_NOT_RESPONDING);
                    break;
                case PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED:
                    notifications.setBridgeStatus(PHNotifications.BridgeStatus.PUSHLINK_BUTTON_NOT_PRESSED);
                    break;
                case PHMessageType.PUSHLINK_AUTHENTICATION_FAILED:
                    notifications.setBridgeStatus(PHNotifications.BridgeStatus.PUSHLINK_AUTHENTICATION_FAILED);
                    break;
                case PHMessageType.BRIDGE_NOT_FOUND:
                    notifications.setBridgeStatus(PHNotifications.BridgeStatus.BRIDGE_NOT_FOUND);
                    break;
                default:
                    notifications.setBridgeStatus(PHNotifications.BridgeStatus.UNKNOWN_ERROR);
            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError: parsingErrorsList) {
                System.out.println("ParsingError : " + parsingError.getMessage());
            }
        }
    };

    public PHSDKListener getListener() {
        System.out.println(listener.toString());
        return listener;
    }

    public PHNotifications.BridgeStatus getBridgeStatus() {
        return notifications.getBridgeStatus();
    }

    public PHBridge getBridge() {
        return phHueSDK.getSelectedBridge();
    }

    public PHBridgeResourcesCache getBridgeCache() {
        return phHueSDK.getSelectedBridge().getResourceCache();
    }
}
