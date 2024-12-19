package com.food.RestaurantRepository;

import com.food.RestaurantEntity.ManageProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageProductRepository extends JpaRepository<ManageProduct,Long> {
}
