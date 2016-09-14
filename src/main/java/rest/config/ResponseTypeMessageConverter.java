package rest.config;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.validation.DefaultMessageCodesResolver;
import rest.constants.ResponseType;
import rest.entity.User;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jtduan on 2016/9/12.
 * 自定义messageConverter将对象转化为字符串，默认为json
 */
public class ResponseTypeMessageConverter extends AbstractHttpMessageConverter<ResponseType> {

    public ResponseTypeMessageConverter(){
        super(new MediaType("text", "x-responseType", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return ResponseType.class.isAssignableFrom(aClass);
    }

    @Override
    protected ResponseType readInternal(Class<? extends ResponseType> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
            String temp = StreamUtils.copyToString(httpInputMessage.getBody(),Charset.forName("UTF-8"));
            int code = Integer.valueOf(temp);
            return ResponseType.of(code);
    }

    @Override
    protected void writeInternal(ResponseType responseType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        String out = responseType.getCode()+":"+responseType.getMsg();
        httpOutputMessage.getBody().write(out.getBytes("utf-8"));
    }
}