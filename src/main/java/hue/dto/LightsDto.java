package hue.dto;


import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import hue.services.BridgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class LightsDto {

	public static final int TRANSITION_TIME = 10; //units??

	@Autowired
	private BridgeService bridgeService;

	public boolean updateLight(String lightId, PHLightState lightState){
        PHLight light = bridgeService.getBridgeCache().getLights().get(lightId);
		lightState.setTransitionTime(TRANSITION_TIME);
		bridgeService.getBridge().updateLightState(light, lightState);
        return true;
	}

	public List<PHLight> getLights() {
        List<PHLight> lights = bridgeService.getBridgeCache().getAllLights();
        // List has first light listed last
        Collections.reverse(lights);
		return lights;
	}
}
