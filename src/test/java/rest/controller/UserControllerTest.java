package rest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.RandomGenerator;
import rest.constants.ResponseType;
import rest.entity.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static rest.controller.BaseControllerTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Test
    @Transactional
    public void testRegisterUser() throws Exception {
        String uri = "/users";
        MvcResult mvcResult = mvc.perform(post(uri)
                .param("email", RandomGenerator.email())
                .param("pwd", "123456")
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("注册返回结果错误", ResponseType.SUCCESS.getResponseStr(),
                mvcResult.getResponse().getContentAsString());

        uri = "/users";
        mvcResult = mvc.perform(post(uri)
                .param("email", "InvilidEmail")
                .param("pwd", "123456")
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("注册返回结果错误", ResponseType.INPUT_ERROR.getResponseStr(),
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    public void testUpdateUser() throws Exception {
        String uri = "/users/"+baseService.getNotExistIdin(User.class);
        MvcResult mvcResult = mvc.perform(put(uri).with(admin()).sessionAttr("user",requestAdmin())
                .param("value", RandomGenerator.email())
                .param("type", "email")
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("更新返回错误", ResponseType.USER_NOTFOUND.getResponseStr(),
                mvcResult.getResponse().getContentAsString());

        uri = "/users/"+baseService.getExistIdinUser();
        mvcResult = mvc.perform(put(uri).with(admin()).sessionAttr("user",requestAdmin())
                .param("value", RandomGenerator.email())
                .param("type", "email2")
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("更新返回错误", ResponseType.USER_FILED_FINAL.getResponseStr(),
                mvcResult.getResponse().getContentAsString());

        uri = "/users/"+baseService.getExistIdinUser();
        mvcResult = mvc.perform(put(uri).with(admin()).sessionAttr("user",requestAdmin())
                .param("value", RandomGenerator.email())
                .param("type", "email")
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("更新返回错误", ResponseType.SUCCESS.getResponseStr(),
                mvcResult.getResponse().getContentAsString());

        uri = "/users/"+baseService.getExistIdinUser();
        mvcResult = mvc.perform(put(uri).with(admin()).sessionAttr("user",requestAdmin())
                .param("value", "InValidEmail")
                .param("type", "email")
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("更新返回错误", ResponseType.INVALID_EMAIL.getResponseStr(),
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    public void testDeleteUser() throws Exception {
        String uri = "/users/"+baseService.getExistIdinUser();
        MvcResult mvcResult = mvc.perform(delete(uri).with(admin())
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("删除返回结果错误", ResponseType.SUCCESS.getResponseStr(),
                mvcResult.getResponse().getContentAsString());

        
        uri = "/users/"+baseService.getNotExistIdin(User.class);
        mvcResult = mvc.perform(delete(uri).with(admin())
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("删除返回结果错误", ResponseType.USER_NOTFOUND.getResponseStr(),
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    public void testPriviliage() throws Exception {
        String uri = "/users/"+baseService.getExistIdinUser();
        MvcResult mvcResult = mvc.perform(put(uri).sessionAttr("user",requestUser())
                .param("value", RandomGenerator.email())
                .param("type", "email")
                .accept(MediaType.ALL)).andReturn();
        mvcResult.getResponse().setCharacterEncoding("utf-8");
        Assert.assertEquals("更新返回错误", ResponseType.PERMISSION_DENIED.getResponseStr(),
                mvcResult.getResponse().getContentAsString());
    }
}
