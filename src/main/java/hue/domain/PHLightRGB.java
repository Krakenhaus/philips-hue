package hue.domain;

import com.philips.lighting.model.PHLight;

/**
 *
 */
public class PHLightRGB {
    private PHLight phLight;
    private int[] rgb;

    public PHLight getPhLight() {
        return phLight;
    }

    public void setPhLight(PHLight phLight) {
        this.phLight = phLight;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int[] rgb) {
        this.rgb = rgb;
    }
}
