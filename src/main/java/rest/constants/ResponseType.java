package rest.constants;

import java.util.NoSuchElementException;

/**
 * Created by jtduan on 2016/9/12.
 */
public enum ResponseType {
    SUCCESS(0,"成功",null),

    USER_NAME_PATTERN_ERROR(1000,"用户名称格式错误",new RuntimeException("用户名称格式错误")),
    USER_NAME_CONFLICT(1001,"用户名称重复",new RuntimeException("用户名称重复")),
    USER_FILED_FINAL(1002,"用户字段不可变",new RuntimeException("用户字段不可变")),
    USER_NOTFOUND(1003,"没有这个用户",new RuntimeException("没有这个用户")),
    INPUT_ERROR(1004,"输入错误",new RuntimeException("输入错误")),
    INVALID_EMAIL(2001,"不合法的Email地址",new RuntimeException("不合法的Email地址")),

    PERMISSION_DENIED(3001,"权限不足",new RuntimeException("权限不足")),
    NAME_ERROR(1,"名称错误",new RuntimeException("名称错误")),
    ORDER_NUM_INVILID(4001,"菜单数量不符合规则",new RuntimeException("菜单数量不符合规则")),
    ORDER_DOING(4002,"菜单已经不可撤销",new RuntimeException("菜单已经不可撤销")),
    FATAL(2,"未知错误",new RuntimeException("未知错误")),
    INVALID_OPERATION(4001,"不支持的操作",new RuntimeException("不支持的操作")),
    NOTEXIST_DISH(4003,"不存在的菜品",new RuntimeException("不存在的菜品")),
    NO_SUFFICIENT_FUND(1005,"余额不足",new RuntimeException("余额不足"));

    private int code;
    private String msg;
    private RuntimeException exception;

    ResponseType(int code,String msg,RuntimeException exception){
        this.code=code;
        this.msg=msg;
        this.exception = exception;
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

    public RuntimeException getException() {
        return exception;
    }

    public void setException(RuntimeException exception) {
        this.exception = exception;
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
