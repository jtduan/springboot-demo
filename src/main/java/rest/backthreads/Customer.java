package rest.backthreads;

import rest.entity.Dish;

/**
 * Created by jtduan on 2016/9/6.
 * 功能：模拟用户随机点菜
 */
public class Customer implements Runnable{

    @Override
    public void run() {
//        Dish dishes = getRandomDish();
//        int num = getRandomNum();
//        order(dishes,num);
    }

    public Dish getRandomDish() {
        return null;
    }
}
