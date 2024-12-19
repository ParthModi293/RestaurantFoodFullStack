package com.food.AdminEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class ManageArea {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long No;


    private String AreaName;

    private String AreaDiscrption;

    @ManyToOne
    @JoinColumn(name = "City_Area")
    private ManageCity city;


}
