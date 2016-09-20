package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.constants.DishType;
import rest.entity.Dish;
import rest.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by hero on 2016/9/5.
 */
public interface DishRepo extends JpaRepository<Dish,Long> {
    public Optional<Dish> findByNameAndType(String name, DishType type);


    @Query("select count(d)>0 from Dish d where d.name = ?1 and d.type =?2")
    public boolean existByNameAndType(String name, DishType type);
}
