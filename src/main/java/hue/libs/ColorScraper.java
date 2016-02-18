package hue.libs;

import hue.exceptions.ImageIOException;
import org.apache.commons.collections4.map.DefaultedMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ColorScraper {
    public static final int GREY_TOLERANCE = 60;
    public static final int COLOR_TOLERANCE = 25;
    public static final int DEFAULT_SCALE_SIZE = 750;

    public static List<int[]> scrapeFromImage(byte[] data, int numColors) throws Exception {
        List<int[]> returnRGBs = new ArrayList<>();
        //data = scale(data, DEFAULT_SCALE_SIZE);
        Map<Integer, Integer> colors = getColorMap(data);

        // Color list sorted by popularity
        List<Map.Entry<Integer, Integer>> colorList = new LinkedList<Map.Entry<Integer, Integer>>(colors.entrySet());
        Collections.sort(colorList, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        int index = 0;

        while(index < (colorList.size() / 3) && (returnRGBs.size() < numColors)) {
            index ++;
            Map.Entry me = colorList.get(index);
            int[] rgb = getRgbArr((Integer) me.getKey());

            if(!isDuplicate(rgb, returnRGBs)) {
                returnRGBs.add(rgb);
            }
        }

        // Fill empty slots with white
        for(int i = (returnRGBs.size() - 1); i < numColors; i++) {
            returnRGBs.add(new int[]{255, 255, 255});
        }

        return returnRGBs;
    }

    public static byte[] scale(byte[] fileData, int maxEdgeLength) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        int height = 0;
        int width = 0;

        try {
            BufferedImage img = ImageIO.read(in);
            if(img.getHeight() > img.getWidth()) {
                height = maxEdgeLength;
            } else {
                width = maxEdgeLength;
            }

            if(height == 0) {
                height = (width * img.getHeight())/ img.getWidth();
            }
            if(width == 0) {
                width = (height * img.getWidth())/ img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            return buffer.toByteArray();
        } catch (IOException e) {
            System.out.println("error occurred while resizing");
            return fileData;
        }
    }

    private static boolean isGrey(int[] rgbArray) {
        int rgDiff = Math.abs(rgbArray[0] - rgbArray[1]);
        int rbDiff = Math.abs(rgbArray[0] - rgbArray[2]);
        int gbDiff = Math.abs(rgbArray[1] - rgbArray[2]);
        return !(rgDiff > GREY_TOLERANCE || rbDiff > GREY_TOLERANCE || gbDiff > GREY_TOLERANCE);
    }

    private static boolean isDuplicate(int[] rgb, List<int[]> existingList) {
        if(existingList.isEmpty()) {
            return false;
        }

        float[] hsv = new float[3];
        Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsv);

        for(int[] existingRgb: existingList) {
            float[] existingHsv = new float[3];
            Color.RGBtoHSB(existingRgb[0], existingRgb[1], existingRgb[2], existingHsv);

            float totalDiff = Math.abs(existingHsv[0]  -hsv[0]) * 360;
            if(totalDiff < COLOR_TOLERANCE) {
                return true;
            }
        }
        return false;
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
}
