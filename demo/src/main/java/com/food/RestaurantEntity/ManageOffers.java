package com.food.RestaurantEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ManageOffers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long No;

    private String CategoryName;


    private String SubcategoryName;

    private String offersName;

    private  String OffersDiscription;

    private java.sql.Date StartDate;

    private java.sql.Date EndDate;

    private Integer Discount;

    @OneToOne
    private ManageProduct product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "Res_Offers")
    private Restaurant restaurant;





}
