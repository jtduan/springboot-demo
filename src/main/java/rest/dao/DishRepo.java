package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.constants.DishType;
import rest.entity.Dish;
import rest.entity.User;

import java.util.List;

/**
 * Created by hero on 2016/9/5.
 */
public interface DishRepo extends JpaRepository<Dish,Long> {
    public Dish findByNameAndType(String name,DishType type);
}
