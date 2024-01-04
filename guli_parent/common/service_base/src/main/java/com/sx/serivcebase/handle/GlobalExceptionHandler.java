package com.sx.serivcebase.handle;

import com.baomidou.mybatisplus.extension.api.R;
import com.sx.commonutils.Result;
import com.sx.serivcebase.exception.GuliException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;


@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("错误"+e.getMessage());
    }
    @ExceptionHandler(GuliException.class)
    public Result error(GuliException e){
        e.printStackTrace();
        return Result.error().message(e.getMsg()).code(e.getCode());
    }
}
