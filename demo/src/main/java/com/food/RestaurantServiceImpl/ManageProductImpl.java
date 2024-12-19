package com.food.RestaurantServiceImpl;

import com.food.AdminEntity.ManageArea;
import com.food.RestaurantEntity.ManageProduct;
import com.food.RestaurantRepository.ManageProductRepository;
import com.food.RestaurantService.ManageProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageProductImpl implements ManageProductService {

    @Autowired
    private ManageProductRepository manageProductRepository;


    @Override
    public List<ManageProduct> getAllProduct() {
        return manageProductRepository.findAll();
    }

    @Override
    public ManageProduct saveProduct(ManageProduct Product) {
        return manageProductRepository.save(Product);
    }

    @Override
    public ManageProduct getProductByNo(Long No) {
        return manageProductRepository.findById(No).get();
    }

    @Override
    public ManageProduct updateProduct(ManageProduct Product) {
        return manageProductRepository.save(Product);
    }

    @Override
    public void deleteProductByNo(Long No) {
manageProductRepository.deleteById(No);
    }

    @Override
    public Page<ManageProduct> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return manageProductRepository.findAll(pageable);
    }
}
