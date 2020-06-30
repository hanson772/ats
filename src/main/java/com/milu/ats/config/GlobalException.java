package com.milu.ats.config;

import com.milu.ats.bean.ReturnVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * @author max.chen
 * @class
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalException {
    @ExceptionHandler({AtsException.class})
    public Object catchBaseException(HttpServletRequest request, HttpServletResponse response, AtsException exception) {
        exception.printStackTrace();
        try {
            log.error(exception.getMessage(), exception);
        }finally {
            return ReturnVO.builder().code(exception.getCode()).msg(exception.getMessage()).data(null).build();
        }
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public Object catchBaseException(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException exception) {
        exception.printStackTrace();
        try {
            log.error(exception.getMessage(), exception);
        }finally {
            return ReturnVO.builder().code(AtsException.ERROR_CODE).msg(exception.getMessage()).data(null).build();
        }

    }

    @ExceptionHandler({Exception.class})
    public Object catchBaseException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        exception.printStackTrace();
        try {
            log.error(exception.getMessage(), exception);
        }finally {
            return ReturnVO.builder().code(AtsException.ERROR_CODE).msg(exception.getMessage()).data(null).build();
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Object catchValidBaseException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        try {
            log.error(exception.getMessage(), exception);
        }finally {
            String message = exception.getBindingResult().getAllErrors().stream().map(x-> x.getDefaultMessage()).collect(Collectors.joining(","));
            return ReturnVO.builder().code(AtsException.ERROR_CODE).msg(message).data(null).build();
        }
    }

}
