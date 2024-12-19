package com.food.AdminEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ManageSubcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long No;


    private String SubcategoryName;

    private String SubcategoryDiscription;

    @ManyToOne
    @JoinColumn(name = "Category_Subcategory")
    private ManageCateory categoryName;




}
