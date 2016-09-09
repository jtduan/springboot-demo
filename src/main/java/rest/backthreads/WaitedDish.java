package rest.backthreads;

import rest.entity.Dish;

import java.time.LocalDateTime;

/**
 * Created by jtduan on 2016/9/6.
 */
public class WaitedDish {
    /**
     * 菜品
     */
    public Dish dish;

    /**
     * 数量
     */
    public int num;

    /**
     * 进入队列时间
     */
    public LocalDateTime in_time;

    public WaitedDish(Dish dish, int num) {
        this.dish = dish;
        this.num = num;
        this.in_time = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaitedDish that = (WaitedDish) o;

        return dish.equals(that.dish);

    }

    @Override
    public int hashCode() {
        return dish.hashCode();
    }
}
