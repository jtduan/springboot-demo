package rest.constants;

import java.util.NoSuchElementException;

/**
 * Created by jtduan on 2016/9/12.
 */
public enum ResponseType {
    SUCCESS(0,"成功"),
    NAME_ERROR(1,"名称错误"),
    FATAL(2,"未知错误");

    private int code;
    private String msg;

    ResponseType(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ResponseType valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= values().length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return values()[ordinal];
    }

    public static ResponseType of(int code) {
        for(ResponseType type:ResponseType.values()){
            if(type.getCode()==code){
                return type;
            }
        }
        throw new NoSuchElementException("NoSuchElement");
    }

}
