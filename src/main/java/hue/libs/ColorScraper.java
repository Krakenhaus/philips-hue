package hue.libs;

import hue.exceptions.ImageIOException;
import org.apache.commons.collections4.map.DefaultedMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 */
public class ColorScraper {
    public static final int GREY_TOLERANCE = 60;

    public static Map<String, Object> scrapeFromImage(File file) {
        Map<String, Object> colors = new HashMap<>();
        return colors;
    }

    private static Map<Integer, Integer> getColorMap(byte[] imageFile) {
        try {
            ImageInputStream is = ImageIO.createImageInputStream(
                    new ByteArrayInputStream(imageFile));
            Iterator iterator = ImageIO.getImageReaders(is);

            if (!iterator.hasNext()) {
                throw new ImageIOException();
            }

            ImageReader imageReader = (ImageReader) iterator.next();
            imageReader.setInput(is);

            BufferedImage image = imageReader.read(0);

            int height = image.getHeight();
            int width = image.getWidth();

            Map<Integer, Integer> map = new DefaultedMap<>(0);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int rgb = image.getRGB(x, y);
                    int[] rgbArr = getRgbArr(rgb);

                    if (!isGrey(rgbArr)) {
                        Integer counter = map.get(rgb);
                        counter++;
                        map.put(rgb, counter);
                    }
                }
            }
            return map;

        } catch (IOException ex) {
            throw new ImageIOException();
        }
    }

    private static int[] getRgbArr(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;

        return new int[]{red, green, blue};
    }

    private static boolean isGrey(int[] rgbArray) {
        int rgDiff = Math.abs(rgbArray[0] - rgbArray[1]);
        int rbDiff = Math.abs(rgbArray[0] - rgbArray[2]);
        int gbDiff = Math.abs(rgbArray[1] - rgbArray[2]);
        return !(rgDiff > GREY_TOLERANCE || rbDiff > GREY_TOLERANCE || gbDiff > GREY_TOLERANCE);
    }
}
