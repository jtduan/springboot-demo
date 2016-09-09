package rest.config;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.controller.UserController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by jtduan on 2016/9/8.
 */
public class ViewHelper {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static String renderJson(String code, String msg) {
        JsonFactory factory = new JsonFactory();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = null;
        try {
            generator = factory.createGenerator(outputStream, JsonEncoding.UTF8);
            generator.writeStartObject();
            generator.writeStringField("code", code);
            generator.writeStringField("msg", msg);
            generator.writeEndObject();
            generator.close();
        } catch (IOException e) {
            logger.error("json构造异常："+code+","+msg);
            return "{\"code\":\"-1\",\"msg\":\"构造json异常\"}";
        }
        return outputStream.toString();
    }

    public static String renderJson(Map map){
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            mapper.writeValue(outputStream, map);
            return outputStream.toString();
        } catch (IOException e) {
            logger.error("json构造异常：");
            return "{\"code\":\"-1\",\"msg\":\"构造json异常\"}";
        }
    }
}
