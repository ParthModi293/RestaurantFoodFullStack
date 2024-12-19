package com.food.AdminEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.RestaurantEntity.ManageProduct;
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
public class ManageCateory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long No;

    private String CategoryName;

    private String CategoryDiscription;

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "Product_Category")
//    private ManageProduct product;

    @OneToMany(mappedBy = "categoryName", cascade = CascadeType.ALL)
    private List<ManageSubcategory> SubcategoryName;





}
