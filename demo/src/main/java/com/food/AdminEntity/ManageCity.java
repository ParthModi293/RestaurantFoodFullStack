package com.food.AdminEntity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food.RestaurantEntity.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long No;

    private String CityName;

    private String CityDiscrption;

    @OneToMany(mappedBy = "city" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ManageArea> areas= new ArrayList<>();


    @Override
    public String toString() {
        return "ManageCity{" +
                "No=" + No +
                ", CityName='" + CityName + '\'' +
                ", CityDiscrption='" + CityDiscrption + '\'' +
                '}';
    }



}
