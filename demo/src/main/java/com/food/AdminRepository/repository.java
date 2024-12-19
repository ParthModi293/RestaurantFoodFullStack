package com.food.AdminRepository;

import com.food.AdminEntity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repository extends JpaRepository<Admin,String> {

    Admin findByUsername(String username);
}
