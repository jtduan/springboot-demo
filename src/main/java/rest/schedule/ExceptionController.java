//package rest.schedule;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import rest.constants.MyErrorType;
//
///**
// * Created by jtduan on 2016/9/12.
// */
//@ControllerAdvice(basePackages = "rest.controller")
//public class ExceptionController {
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public @ResponseBody
//    MyErrorType greetingExceptionHandler(Exception ex) {
//        System.out.println("greetingExceptionHandler");
//        return new MyErrorType(0,ex.getMessage());
//    }
//}
