package hue.dto;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import hue.domain.CustomLightState;
import hue.domain.HueSDK;
import hue.exceptions.InvalidBrightnessException;
import hue.exceptions.InvalidRGBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class LightsDto {

	public static final int TRANSITION_TIME = 1000; //ms

	@Autowired
	private HueSDK hueSDK;
	
	public PHLight setLightRGB(String id, CustomLightState customLightState){
		int r = customLightState.getR();
		int g = customLightState.getG();
		int b = customLightState.getB();
		int brightness = customLightState.getBrightness();
		boolean on = customLightState.getIsOn();

		if((r < 0 || r > 255) ||(g < 0 || g > 255) || (b < 0 || b > 255)) {
			throw new InvalidRGBException();
		}

		if((brightness < 1) || (brightness > 254)) {
			throw new InvalidBrightnessException();
		}
		
		PHBridge bridge = hueSDK.getSelectedBridge();
        PHBridgeResourcesCache cache = bridge.getResourceCache();
        PHLight light = cache.getLights().get(id);
        
        PHLightState lightState = new PHLightState();

		// Transform RGB color to XY color
        float xy[] = PHUtilities.calculateXYFromRGB(r, g, b	, light.getModelNumber());
	    lightState.setX(xy[0]);
		lightState.setY(xy[1]);
		lightState.setBrightness(brightness);
		lightState.setOn(on);
		lightState.setTransitionTime(TRANSITION_TIME);
		
		bridge.updateLightState(light, lightState);    
        
        return light;
	}

	public List<PHLight> getLights() {
		PHBridge bridge = hueSDK.getSelectedBridge();

		PHBridgeResourcesCache cache = bridge.getResourceCache();

        List<PHLight> lights = cache.getAllLights();
        Collections.reverse(lights);
        
		return lights;
	}

}
