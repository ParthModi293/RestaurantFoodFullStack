package com.food.AdminSevice;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageSubcategory;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManageSubcategoryService {

    List<ManageSubcategory> getAllSubcategory();

    ManageSubcategory saveSubcategory(ManageSubcategory Subcategory);

    ManageSubcategory getSubcategoryByNo(Long No);

    ManageSubcategory updateSubcategory(ManageSubcategory Subcategory);

    void deleteSubcategoryByNo(Long No);

    Page<ManageSubcategory> findPaginated(int pageNo, int pageSize);
}
