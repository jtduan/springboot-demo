package rest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import rest.entity.User;
import rest.util.TestBaseService;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {

    public static MockMvc mvc;

    public static TestBaseService baseService;

    public static RequestPostProcessor admin() {
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority("ADMIN");
        return user("admin").authorities(authorities);
    }

    public static RequestPostProcessor simpleUser() {
//        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority("USER");
        return user("user");//.authorities(authorities);
    }

    public static User requestAdmin(){
        return baseService.InsertRandomAdmin();
    }

    public static User requestUser(){
        return baseService.InsertRandomUser();
    }

    @Test
    public void testMainPage() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.ALL)).andReturn();
        Assert.assertEquals("返回status不为200",mvcResult.getResponse().getStatus(),200);
        Assert.assertTrue("主页需包含Welcome",mvcResult.getResponse().getContentAsString().contains("Welcome"));
    }

    @Test
    public void testLoginPage() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/login").accept(MediaType.ALL)).andReturn();
        Assert.assertEquals("返回status不为200",mvcResult.getResponse().getStatus(),200);
        Assert.assertTrue("主页需包含Welcome",mvcResult.getResponse().getContentAsString().contains("username"));
    }
}

@Component
class MVCInjector {
    @Autowired
    WebApplicationContext webApplicationConnect;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    TestBaseService service;

    @PostConstruct
    public void postConstruct() {
        BaseControllerTest.mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect)
                .addFilters(springSecurityFilterChain)
                .build();
        BaseControllerTest.baseService=service;
    }
}