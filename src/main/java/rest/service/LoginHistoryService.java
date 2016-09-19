package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void loginSuccess(long userId,String ip){
        LoginHistory history = new LoginHistory(SpringUtil.getEntityManager().getReference(User.class,userId),ip);
        loginHistoryRepo.save(history);
    }

    @Transactional
    public void loginFailure(String username, String password, String ip) {
        LoginHistory history = new LoginHistory(username,password,ip);
        loginHistoryRepo.save(history);
    }
}
