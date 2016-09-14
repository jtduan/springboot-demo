package rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Test;
import rest.constants.DateUtil;
import rest.constants.RandomGenerator;
import rest.entity.User;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class JsonTest {

    /**
     * 测试JsonConfig序列化和反序列化
     * @throws IOException
     */
    @Test
    public void TestSerializerAndDeserializer() throws IOException {
        User u = RandomGenerator.getRandomUser();
        u.setCreateTime(LocalDateTime.now());

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new JsonConfig.Serializer());
        mapper.registerModule(module);
        module.addDeserializer(LocalDateTime.class, new JsonConfig.Deserializer());
        mapper.registerModule(module);

        String serialized = mapper.writeValueAsString(u);
        assertTrue(serialized.contains("\""+DateUtil.getDateTimeStr(u.getCreateTime())+"\""));

        User DeserilableUser = mapper.readValue(serialized, User.class);
        assertTrue(DeserilableUser.getCreateTime().getMinute()==u.getCreateTime().getMinute());
    }

}
