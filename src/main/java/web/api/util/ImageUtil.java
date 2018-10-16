package web.api.util;

/**
 * Created by oleht on 14.10.2018
 */
public class ImageUtil {

    public static Byte[] convertBytes(byte[] byteArray) {
        Byte[] byteObjects = new Byte[byteArray.length];

        int i = 0;

        for (byte b : byteArray) {
            byteObjects[i++] = b;
        }

        return byteObjects;
    }

    public static byte[] convertBytes(Byte[] byteArray) {
        byte[] byteObjects = new byte[byteArray.length];

        int i = 0;

        for (byte b : byteArray) {
            byteObjects[i++] = b;
        }

        return byteObjects;
    }
}
