package rest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static rest.controller.BaseControllerTest.mvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DishControllerTest{

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testIndex() throws Exception {
        String uri = "/dishes";
        MvcResult mvcResult = mvc.perform(get(uri).accept(MediaType.ALL)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertTrue("应该发生重定向", status == 302);
    }

    @Test
    /**
     * @WithMockUser(username="admin",roles={"USER","ADMIN"})
     * @WithUserDetails("jtduan@qq.com")
     * 这两种注解仅限于方法调用时有效（Service层）不能用于controller
     **/
    public void testDishLists() throws Exception {
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority("ADMIN");
        String uri = "/dishes";
        MvcResult mvcResult = mvc
                .perform(get("/dishes").with(user("admin").password("pass").authorities(authorities)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals("应该返回200", 200, status);
        Assert.assertTrue("列表需包含<table>标签",mvcResult.getResponse().getContentAsString().contains("<table>"));
    }
}
