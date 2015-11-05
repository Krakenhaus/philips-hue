package hue.services;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import hue.dto.LightsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PHLightService {
    @Autowired
    LightsDto lightsDto;

    public boolean updateLight(String lightId, PHLightState lightState) {
        return lightsDto.updateLight(lightId, lightState);
    }

    public List<PHLight> getLights() {
        return lightsDto.getLights();
    }

    public float[] convertRGBToXY(int[] rgb, String modelNumber) {
        return PHUtilities.calculateXYFromRGB(rgb[0], rgb[1], rgb[2], modelNumber);
    }


}
