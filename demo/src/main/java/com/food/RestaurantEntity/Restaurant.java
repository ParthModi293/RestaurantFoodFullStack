package com.food.RestaurantEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageCity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String email;
    private String contact;
    private String address;


    private String City;


    private String area;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "restaurant")
    List<ManageOffers> offers;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<ManageProduct> product;



}
