package hue.services;

import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import hue.dto.LightsDto;
import hue.libs.ColorScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public boolean setLightsToImage(byte[] fileData, List<String> lightIds) {
        List<float[]> xyList = new ArrayList<>();
        List<int[]> rgbList;
        boolean isSuccessful = true;

        try {
            rgbList = ColorScraper.scrapeFromImage(fileData, lightIds.size());
        } catch (Exception ex) {
            return false;
        }

        for(int i = 0; i < lightIds.size(); i++) {
            PHLightState lightState = new PHLightState();
            float[] xy = convertRGBToXY(rgbList.get(i), lightsDto.getModelNumber(lightIds.get(i)));
            xyList.add(xy);

            lightState.setX(xy[0]);
            lightState.setY(xy[1]);
            isSuccessful = isSuccessful && updateLight(lightIds.get(i), lightState);
        }
        return isSuccessful;
    }
}
