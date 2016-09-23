package rest.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.CurrentUserUtils;
import rest.constants.ResponseType;
import rest.entity.Dish;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static rest.controller.BaseControllerTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
//    @Test
//    @Transactional
//    public void testOrder() throws Exception {
//        String uri = "/order/"+baseService.getExistIdinDish();
//        MvcResult mvcResult = mvc.perform(post(uri).with(admin())
//                .sessionAttr(CurrentUserUtils.INSTANCE.CUR_USER,requestAdmin().getId())
//                .accept(MediaType.ALL)).andReturn();
//        mvcResult.getResponse().setCharacterEncoding("utf-8");
//        Assert.assertEquals("管理员点单", ResponseType.SUCCESS.getResponseStr(),
//                mvcResult.getResponse().getContentAsString());
//
//        uri = "/order/"+baseService.getExistIdinDish();
//        mvcResult = mvc.perform(post(uri).with(user(baseService.getTestUserId()+""))
//                .sessionAttr(CurrentUserUtils.INSTANCE.CUR_USER,baseService.getTestUser().getId())
//                .accept(MediaType.ALL)).andReturn();
//        mvcResult.getResponse().setCharacterEncoding("utf-8");
//        Assert.assertEquals("消费者点单", ResponseType.SUCCESS.getResponseStr(),
//                mvcResult.getResponse().getContentAsString());
//
//        uri = "/order/"+baseService.getExistIdinDish();
//        mvcResult = mvc.perform(post(uri).with(user(baseService.getTestUserId()+""))
//                .sessionAttr(CurrentUserUtils.INSTANCE.CUR_USER,baseService.getTestUser2().getId())
//                .accept(MediaType.ALL)).andReturn();
//        mvcResult.getResponse().setCharacterEncoding("utf-8");
//        Assert.assertEquals("没有权限点别人的单", ResponseType.PERMISSION_DENIED.getResponseStr(),
//                mvcResult.getResponse().getContentAsString());
//
//        uri = "/order/"+baseService.getNotExistIdin(Dish.class);
//        mvcResult = mvc.perform(post(uri).with(admin())
//                .sessionAttr(CurrentUserUtils.INSTANCE.CUR_USER,requestAdmin().getId())
//                .accept(MediaType.ALL)).andReturn();
//        mvcResult.getResponse().setCharacterEncoding("utf-8");
//        Assert.assertEquals("点不存在的菜单", ResponseType.NOTEXIST_DISH.getResponseStr(),
//                mvcResult.getResponse().getContentAsString());
//    }

}
