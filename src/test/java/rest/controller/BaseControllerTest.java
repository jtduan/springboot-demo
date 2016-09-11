package rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rest.dao.UserRepo;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {

    public static MockMvc mvc;

//    @Autowired
//    WebApplicationContext webApplicationConnect;
//
//    @BeforeClass
//    public static void setUp() throws JsonProcessingException {
//        mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
//    }
}

@Component
class MVCInjector {
    @Autowired
    WebApplicationContext webApplicationConnect;

    @PostConstruct
    public void postConstruct() {
        BaseControllerTest.mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();
    }
}