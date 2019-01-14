package web.api.application.exception;

/**
 * Created by oleht on 14.10.2018
 */
public class ImageConvertException extends RuntimeException {
    public ImageConvertException(String message) {
        super(message);
    }

    public ImageConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
