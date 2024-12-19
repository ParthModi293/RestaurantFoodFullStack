package com.food.AdminServiceImple;

import com.food.AdminEntity.ManageCity;
import com.food.AdminRepository.ManageCityRepository;
import com.food.AdminSevice.ManageCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageCityImpl implements ManageCityService {

    @Autowired
    private ManageCityRepository manageCityRepository;

    @Override
    public List<ManageCity> getAllCity() {
        return manageCityRepository.findAll();
    }

    @Override
    public ManageCity saveCity(ManageCity city) {
        return manageCityRepository.save(city);
    }

    @Override
    public ManageCity getCityByNo(Long No) {
        return manageCityRepository.findById(No).get();
    }

    @Override
    public ManageCity updateCity(ManageCity city) {
        return manageCityRepository.save(city);
    }

    @Override
    public void deleteCityByNo(Long No) {
        manageCityRepository.deleteById(No);
    }

    @Override
    public Page<ManageCity> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return manageCityRepository.findAll(pageable);
    }
}
