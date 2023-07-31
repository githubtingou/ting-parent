package org.ting.minio.exception;

/**
 * 自定义 minio异常
 *
 * @author ting
 * @version 1.0
 * @date 2023/6/19
 */
public class CustomMinioException extends RuntimeException {

    public CustomMinioException() {
        super();
    }

    public CustomMinioException(String message) {
        super(message);
    }

    public CustomMinioException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomMinioException(Throwable cause) {
        super(cause);
    }
 
}
