package rest.dao;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import rest.constants.DishType;
import rest.entity.Dish;
import rest.entity.Restaurant;
import rest.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by hero on 2016/9/5.
 * Hibernate本身不支持Redis作为缓存
 * 使用SpringDataJPA可以使用Redis作为二级缓存
 * 注意：findOne()不会从缓存中读取，findOne()读取结果也不会进入缓存
 */
@CacheConfig(cacheNames = "rest.entity.Dish")
@Cacheable
public interface DishRepo extends JpaRepository<Dish,Long> {
    public Optional<Dish> findByNameAndType(String name,DishType type);

    public Optional<Dish> findById(long id);

    public List<Dish> findByRestaurant(Restaurant restaurant);
}
