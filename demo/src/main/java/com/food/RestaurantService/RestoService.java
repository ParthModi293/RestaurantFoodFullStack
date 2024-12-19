package com.food.RestaurantService;

import com.food.RestaurantEntity.Restaurant;
import com.food.RestaurantRepository.RestaurantRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestoService {

    @Autowired
    private RestaurantRepository restaurantRepository;








    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }


    public Restaurant authenticate(String restaurantName, String email) {
        return restaurantRepository.findByRestaurantNameAndEmail(restaurantName, email);
    }



}
