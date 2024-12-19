package com.food.RestaurantRepository;

import com.food.RestaurantEntity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Restaurant findByRestaurantNameAndEmail(String restaurantName, String email);

}
