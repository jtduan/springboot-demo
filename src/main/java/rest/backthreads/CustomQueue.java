package rest.backthreads;

import rest.entity.Dish;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by jtduan on 2016/9/7.
 */
public class CustomQueue {
    public static Thread t;

    public static Queue<WaitedDish> queue = new LinkedBlockingQueue<>();

    public static synchronized void push(Dish dish,int num){
        Iterator<WaitedDish> it = queue.iterator();
        while(it.hasNext()){
            WaitedDish temp = it.next();
            if(temp.equals(dish)){
                temp.num+=num;
                return;
            }
        }
        queue.add(new WaitedDish(dish,num));
        if(queue.size()==1){
            LockSupport.unpark(t);
        }
    }
    public static synchronized WaitedDish poll() {
        return queue.poll();
    }
}
