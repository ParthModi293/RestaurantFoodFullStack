package com.food.AdminServiceImple;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageCity;
import com.food.AdminRepository.ManageAreaRepository;
import com.food.AdminSevice.ManageAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageAreaImpl implements ManageAreaService{

    @Autowired
    private ManageAreaRepository manageAreaRepository;

    @Override
    public List<ManageArea> getAllArea() {
        return manageAreaRepository.findAll();
    }

    @Override
    public ManageArea saveArea(ManageArea Area) {
        return manageAreaRepository.save(Area);
    }

    @Override
    public ManageArea getAreaByNo(Long No) {
        return manageAreaRepository.findById(No).get();
    }

    @Override
    public ManageArea updateArea(ManageArea Area) {
         return manageAreaRepository.save(Area);
    }

    @Override
    public void deleteAreaByNo(Long No) {
        manageAreaRepository.deleteById(No);
    }

    @Override
    public Page<ManageArea> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return manageAreaRepository.findAll(pageable);
    }

}
