package web.api.application.util;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
