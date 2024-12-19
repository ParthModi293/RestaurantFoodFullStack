package com.food.AdminEntity;

import com.food.RestaurantEntity.Restaurant;
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
public class ManageComplaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long No;

    private String Subject;

    private String Discription;

    private Date ComplaintDate;

    private String Reply;

    private Date ReplyDate;

    private String Attachment;

    private String Status;

    @ManyToOne
    private Restaurant restaurant;


}
