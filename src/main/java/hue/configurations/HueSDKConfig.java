package hue.configurations;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import hue.domain.HueSDK;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class HueSDKConfig {

    private PHHueSDK phHueSDK;
    private HueSDK hueSDK = new HueSDK(phHueSDK);

    // TODO - Make this dynamic???
    @Value("${bridge.ip}")
    private String ip;

    // TODO - Make this dynamic???
    @Value("${bridge.username}")
    private String username;

    @Bean
    @PostConstruct
    public HueSDK initPHHueSDK() {
        PHHueSDK phHueSDK = PHHueSDK.create();

        findBridges();
        phHueSDK.getNotificationManager().registerSDKListener(getListener());

        return hueSDK;
    }

    private void findBridges() {
        phHueSDK = PHHueSDK.getInstance();
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
    }

    private PHSDKListener listener = new PHSDKListener() {

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {
            System.out.println("found access point");

            System.out.println(username);
            PHHueSDK phHueSDK = PHHueSDK.getInstance();
            accessPointsList.get(0).setUsername(username);
            phHueSDK.connect(accessPointsList.get(0));

        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            // Start the Pushlink Authentication.
            phHueSDK.startPushlinkAuthentication(accessPoint);
            System.out.println("auth_required");
        }

        @Override
        public void onBridgeConnected(PHBridge bridge) {
            phHueSDK.setSelectedBridge(bridge);
            phHueSDK.enableHeartbeat(phHueSDK.getSelectedBridge(), PHHueSDK.HB_INTERVAL);

            String lastIpAddress =  bridge.getResourceCache().getBridgeConfiguration().getIpAddress();

            System.out.println("found bridge");
            System.out.println("On connected: IP " + lastIpAddress);
            System.out.println("ip: " + lastIpAddress + ", username: " + username);

            hueSDK.setStatus(HueSDK.HEALTHY);
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
            if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                hueSDK.setStatus(HueSDK.BRIDGE_NOT_RESPONDING);
            }
            else if (code == PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED) {
                hueSDK.setStatus(HueSDK.PUSHLINK_BUTTON_NOT_PRESSED);
            }
            else if (code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                hueSDK.setStatus(HueSDK.PUSHLINK_AUTHENTICATION_FAILED);
            }
            else if (code == PHMessageType.BRIDGE_NOT_FOUND) {
               hueSDK.setStatus(HueSDK.BRIDGE_NOT_FOUND);
            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError: parsingErrorsList) {
                System.out.println("ParsingError : " + parsingError.getMessage());
            }
        }
    };

    public void restartHeartbeat() {
        phHueSDK.disableHeartbeat(phHueSDK.getSelectedBridge());
        phHueSDK.enableHeartbeat(phHueSDK.getSelectedBridge(), PHHueSDK.HB_INTERVAL);

    }

    public PHSDKListener getListener() {
        System.out.println(listener.toString());
        return listener;
    }

    public void setListener(PHSDKListener listener) {
        this.listener = listener;
    }
}




