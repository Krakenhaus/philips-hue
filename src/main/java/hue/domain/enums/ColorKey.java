package hue.domain.enums;

/**
 *
 */
public enum ColorKey {
    PRIMARY("primary"),
    ACCENT("accent"),
    OTHERS("others"),
    NOT_SUPPORTED("");

    private String key;

    private ColorKey(String key) {
        this.key = key;
    }

    public static ColorKey getEnum(String key) {
        for(ColorKey colorEnum : ColorKey.values()) {
            if (key.toLowerCase().contains(colorEnum.key.toLowerCase())) {
                return colorEnum;
            }
        }

        return ColorKey.NOT_SUPPORTED;
    }

    @Override
    public String toString() {
        return key;
    }
}
