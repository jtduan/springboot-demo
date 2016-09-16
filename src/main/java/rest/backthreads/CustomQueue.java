package rest.backthreads;

import rest.constants.ResponseType;
import rest.entity.Dish;
import rest.entity.Order;
import rest.entity.User;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jtduan on 2016/9/7.
 */

public enum CustomQueue {
    INSTANCE;

    public Thread t;

    public Queue<WaitedDish> queue = new LinkedBlockingQueue<>();

    public synchronized void push(Order order){
        Iterator<WaitedDish> it = queue.iterator();
        while(it.hasNext()){
            WaitedDish temp = it.next();
            if(temp.dish_name.equals(order.getDish_name())){
                temp.orders.add(order);
                return;
            }
        }
        queue.add(new WaitedDish(order));
        if(queue.size()==1){
            LockSupport.unpark(t);
        }
    }

    public synchronized void remove(Order order){
        Iterator<WaitedDish> it = queue.iterator();
        while(it.hasNext()){
            WaitedDish temp = it.next();
            if(temp.dish_name.equals(order.getDish_name())){
                Queue<Order> orders = temp.orders;
                Iterator<Order> inner_it = orders.iterator();
                while(inner_it.hasNext()) {
                    Order o = inner_it.next();
                    if(order.getId() == o.getId()){
                        inner_it.remove();
                        return;
                    }
                }
            }
        }
    }

    public ResponseType press(Order d){
        return ResponseType.SUCCESS;
    }

    public synchronized WaitedDish poll() {
        return queue.poll();
    }
}
