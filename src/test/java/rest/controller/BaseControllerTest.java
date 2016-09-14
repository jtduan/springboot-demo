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
import rest.constants.Role;
import rest.constants.VIP;
import rest.entity.User;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import java.util.EnumSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {

    public static MockMvc mvc;

    public static RequestPostProcessor admin() {
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority("ADMIN");
        return user("jtduan@qq.com").authorities(authorities);
    }

    public static User requestUser(){
        User u = new User("jtduan@qq.com","jtduan","jtduan", VIP.VIP1);
        u.setId(1l);
        u.setRoles(EnumSet.of(Role.ADMIN));
        return u;
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

    @PostConstruct
    public void postConstruct() {
        BaseControllerTest.mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect)
                .addFilters(springSecurityFilterChain)
                .build();
    }
}