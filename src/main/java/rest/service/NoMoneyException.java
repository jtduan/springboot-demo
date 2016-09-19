package rest.service;

/**
 * Created by jtduan on 2016/9/19.
 * 余额不足时抛出异常
 */
public class NoMoneyException extends RuntimeException {

    public NoMoneyException(String message) {
        super(message);
    }
}
