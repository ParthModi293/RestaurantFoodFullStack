package com.food.AdminServiceImple;

import com.food.AdminEntity.ManageCateory;
import com.food.AdminEntity.ManageCity;
import com.food.AdminRepository.ManageCategoryRepository;
import com.food.AdminSevice.ManageCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageCategoryImpl implements ManageCategoryService {

    @Autowired
    private ManageCategoryRepository manageCategoryRepository;

    @Override
    public List<ManageCateory> getAllCategory() {
        return manageCategoryRepository.findAll();
    }

    @Override
    public ManageCateory saveCategory(ManageCateory Category) {
        return  manageCategoryRepository.save(Category);
    }

    @Override
    public ManageCateory getCategoryByNo(Long No) {
        return manageCategoryRepository.findById(No).get();
    }

    @Override
    public ManageCateory updateCategory(ManageCateory Category) {
        return manageCategoryRepository.save(Category);
    }

    @Override
    public void deleteCategoryByNo(Long No) {
        manageCategoryRepository.deleteById(No);
    }

    @Override
    public Page<ManageCateory> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return manageCategoryRepository.findAll(pageable);
    }
}
