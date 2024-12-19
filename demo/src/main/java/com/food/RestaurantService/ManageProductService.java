package com.food.RestaurantService;


import com.food.AdminEntity.ManageArea;
import com.food.RestaurantEntity.ManageProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManageProductService {


    List<ManageProduct> getAllProduct();

    ManageProduct saveProduct(ManageProduct Product);


    ManageProduct getProductByNo(Long No);

    ManageProduct updateProduct(ManageProduct Product);

    void deleteProductByNo(Long No);


    Page<ManageProduct> findPaginated(int pageNo, int pageSize);



}
