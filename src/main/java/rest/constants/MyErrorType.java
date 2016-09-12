package rest.constants;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;

/**
 * Created by jtduan on 2016/9/12.
 */
public class MyErrorType implements Serializable{
    private int code;
    private String message;
    public MyErrorType(int value, String message) {
        this.code=value;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
