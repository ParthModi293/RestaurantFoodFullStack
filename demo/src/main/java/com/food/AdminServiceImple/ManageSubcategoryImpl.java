package com.food.AdminServiceImple;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageSubcategory;
import com.food.AdminRepository.ManageSubcategoryRepository;
import com.food.AdminSevice.ManageSubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageSubcategoryImpl implements ManageSubcategoryService {


    @Autowired
    private ManageSubcategoryRepository manageSubcategoryRepository;

    @Override
    public List<ManageSubcategory> getAllSubcategory() {
        return manageSubcategoryRepository.findAll();
    }

    @Override
    public ManageSubcategory saveSubcategory(ManageSubcategory Subcategory) {
        return manageSubcategoryRepository.save(Subcategory);
    }

    @Override
    public ManageSubcategory getSubcategoryByNo(Long No) {
        return manageSubcategoryRepository.findById(No).get();
    }

    @Override
    public ManageSubcategory updateSubcategory(ManageSubcategory Subcategory) {
        return manageSubcategoryRepository.save(Subcategory);
    }

    @Override
    public void deleteSubcategoryByNo(Long No) {
manageSubcategoryRepository.deleteById(No);
    }

    @Override
    public Page<ManageSubcategory> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return manageSubcategoryRepository.findAll(pageable);
    }

}
