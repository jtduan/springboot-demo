package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rest.constants.SpringUtil;
import rest.dao.AccessRepo;
import rest.entity.AccessHistory;
import rest.entity.User;

/**
 * Created by jtduan on 2016/9/13.
 */
@Service
public class AccessHistoryService {

    @Autowired
    private AccessRepo accessRepo;

    @Transactional
    public void saveAccessHistory(User user,String ip,String url) {
        if(user == null){
            AccessHistory history = new AccessHistory(null,ip,url);
            accessRepo.save(history);
        }
        else {
            AccessHistory history = new AccessHistory(SpringUtil.getEntityManager().getReference(User.class, user.getId()), ip, url);
            accessRepo.save(history);
        }
    }
}
