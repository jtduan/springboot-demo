package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.constants.SpringUtil;
import rest.dao.LoginHistoryRepo;
import rest.entity.LoginHistory;
import rest.entity.User;

/**
 * Created by jtduan on 2016/9/13.
 */
@Service
public class LoginHistoryService {
    @Autowired
    private LoginHistoryRepo loginHistoryRepo;

    public void loginSuccess(User user,String ip){
        LoginHistory history = new LoginHistory(SpringUtil.getEntityManager().getReference(User.class,user.getId()),ip);
        loginHistoryRepo.save(history);
    }

    public void loginFailure(String username, String password, String ip) {
        LoginHistory history = new LoginHistory(username,password,ip);
        loginHistoryRepo.save(history);
    }
}
