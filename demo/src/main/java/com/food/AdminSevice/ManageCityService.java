package com.food.AdminSevice;

import com.food.AdminEntity.ManageCity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManageCityService {


    List<ManageCity> getAllCity();

    ManageCity saveCity(ManageCity city);


    ManageCity getCityByNo(Long No);

    ManageCity updateCity(ManageCity city);

    void deleteCityByNo(Long No);


    Page<ManageCity> findPaginated(int pageNo, int pageSize);


}
