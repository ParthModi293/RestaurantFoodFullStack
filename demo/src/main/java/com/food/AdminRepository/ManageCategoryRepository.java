package com.food.AdminRepository;

import com.food.AdminEntity.ManageCateory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageCategoryRepository extends JpaRepository<ManageCateory,Long> {
}
