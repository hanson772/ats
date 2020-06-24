package com.milu.ats.config;

import lombok.Builder;
import lombok.Data;

/**
 * @author max.chen
 * @class
 */
@Data
@Builder
public class AtsException extends RuntimeException {
    final static int ERROR_CODE = 1;
    int code;
    String message;
    Throwable thrown;

    public AtsException(String message){
        super(message);
        this.code = ERROR_CODE;
    }

    public AtsException(int code, String message){
        super(message);
        this.code = code;
    }
    public AtsException(String message, Throwable e){
        super(message, e);
        this.code = ERROR_CODE;
    }
    public AtsException(int code, String message, Throwable e){
        super(message, e);
        this.code = code;
    }

}
