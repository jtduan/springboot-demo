package rest.constants;

import java.util.NoSuchElementException;

/**
 * Created by jtduan on 2016/9/12.
 */
public enum ResponseType {
    SUCCESS(0,"成功"),

    USER_NAME_PATTERN_ERROR(1000,"用户名称格式错误"),
    USER_NAME_CONFLICT(1001,"用户名称重复"),
    USER_FILED_FINAL(1002,"用户字段不可变"),
    USER_NOTFOUND(1003,"没有这个用户"),
    INPUT_ERROR(1004,"输入错误"),
    INVALID_EMAIL(2001,"不合法的Email地址"),

    PERMISSION_DENIED(3001,"权限不足"),
    NAME_ERROR(1,"名称错误"),
    FATAL(2,"未知错误"), INVALID_OPERATION(4001,"不支持的操作");

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

    public String getResponseStr() {
        return code+":"+msg;
    }
}
