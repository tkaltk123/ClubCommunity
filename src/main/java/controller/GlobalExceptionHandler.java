package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    //@Valid 어노테이션에서 발생하는 예외를 잡음
    //기본 메시지 리스트를 반환
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<List<String>> invalidException(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        ArrayList<String> res = new ArrayList<>();
        for(FieldError fe : result.getFieldErrors()){
            res.add(fe.getField() +"은(는) "+fe.getDefaultMessage());
        }
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
    //예외 클래스를 직접 정의하지 않고 런타임 예외를 모두 잡음
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseEntity<String> GlobalException(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
