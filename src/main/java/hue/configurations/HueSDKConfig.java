/*package hue.configurations;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class HueSDKConfig {

    private PHHueSDK phHueSDK;

    @Bean
    @PostConstruct
    public PHHueSDK initPHHueSDK() {
        phHueSDK = PHHueSDK.create();

        findBridges();
        phHueSDK.getNotificationManager().registerSDKListener(getListener());
        return phHueSDK;
    }


    public void findBridges() {
        phHueSDK = PHHueSDK.getInstance();
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);

        //connectToAccessPoint("newdeveloper", "192.168.1.7");
    }

    private PHSDKListener listener = new PHSDKListener() {

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {
            System.out.println("found access point");

            System.out.println(username);
            PHHueSDK phHueSDK = PHHueSDK.getInstance();
            //accessPointsList.get(0).setUsername("newdeveloper");
            //phHueSDK.connect(accessPointsList.get(0))

            System.out.println(accessPointsList);

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
            //ip = bridge.getResourceCache().getBridgeConfiguration().getIpAddress();
            phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
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
                //hueSDK.setStatus(HueSDK.BRIDGE_NOT_RESPONDING);
            }
            else if (code == PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED) {
                //hueSDK.setStatus(HueSDK.PUSHLINK_BUTTON_NOT_PRESSED);
            }
            else if (code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                //hueSDK.setStatus(HueSDK.PUSHLINK_AUTHENTICATION_FAILED);
            }
            else if (code == PHMessageType.BRIDGE_NOT_FOUND) {
               //hueSDK.setStatus(HueSDK.BRIDGE_NOT_FOUND);
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

    public void setListener(PHSDKListener listener) {
        this.listener = listener;
    }
}




*/