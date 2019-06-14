package cn.eblcu.questionbank.ui.exception;

import cn.eblcu.questionbank.ui.model.BaseModle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
  * @Author hanchuang
  * @Version 1.0
  * @Date add on 2019/6/3
  * @Description   异常统一处理
  */
@RestController
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    /**
     * @Author 焦冬冬
     * @Description  业务类异常统一处理
     * @Date 11:43 2019/6/10
     * @Param
     * @return
     **/
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseModle handlerBusinessException(BusinessException businessException){
        log.info("业务异常："+businessException.toString());
        return BaseModle.getFailData(businessException.getCode(),businessException.getDescribe());
    }
    /*处理接口参数异常*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> handlerMethodArgumentException(MethodArgumentNotValidException e){
        log.error("参数校验异常："+e.getMessage(),e);
        BindingResult bindingResult = e.getBindingResult();
        return new ResponseEntity<>(bindingResult.getFieldError().toString(), HttpStatus.BAD_REQUEST);
    }


    /*处理系统异常*/
    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("系统异常!"+e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
