package rest.secruityform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rest.controller.BaseControllerTest;
import rest.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginFormTest extends BaseControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void testLogin() throws Exception {
        mvc.perform(formLogin().user("jtduan@qq.com").password("jtduan"))
                .andExpect(authenticated().withUsername(userService.findbyEmail("jtduan@qq.com").getId()+""));
    }
}
