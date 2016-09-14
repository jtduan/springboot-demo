package rest.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.springframework.boot.jackson.JsonComponent;
import rest.constants.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by jtduan on 2016/9/14.
 */
@JsonComponent
public class JsonConfig {

    public static class Serializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider serializers) throws IOException, JsonProcessingException {
            jgen.writeString(DateUtil.getDateTimeStr(value));
        }
    }

    public static class Deserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return DateUtil.getDateTime(jp.getValueAsString());
        }
    }

}
