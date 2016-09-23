package rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rest.entity.Restaurant;

import java.util.Optional;

/**
 * Created by djt on 9/17/16.
 */
public interface RestaurantRepo extends JpaRepository<Restaurant,Long>{

    public Optional<Restaurant> findById(long id);

    @Query("select count(r) > 0 from Restaurant r where r.name = ?1")
    public boolean existByName(String name);
}
