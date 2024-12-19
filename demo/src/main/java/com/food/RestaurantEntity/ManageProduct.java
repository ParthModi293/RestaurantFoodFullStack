package com.food.RestaurantEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.AdminEntity.ManageCateory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ManageProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long No;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<ManageCateory> CategoryName;

    private String Category;

    private String SubcategoryName;

    private String ProductName;

    private Integer ProductPrize;

    private  String ProductDiscription;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Res_Product")
    private Restaurant restaurant;

    @OneToOne
    private ManageOffers Offers;



}
