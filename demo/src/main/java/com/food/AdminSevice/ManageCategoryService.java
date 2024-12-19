package com.food.AdminSevice;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageCateory;
import com.food.AdminEntity.ManageCity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManageCategoryService {

    List<ManageCateory> getAllCategory();

    ManageCateory saveCategory(ManageCateory Category);

    ManageCateory getCategoryByNo(Long No);

    ManageCateory updateCategory(ManageCateory Category);

    void deleteCategoryByNo(Long No);

    Page<ManageCateory> findPaginated(int pageNo, int pageSize);




}
