package com.food.RestaurantServiceImpl;

import com.food.RestaurantEntity.ManageOffers;
import com.food.RestaurantEntity.ManageProduct;
import com.food.RestaurantRepository.ManageOffersRepository;
import com.food.RestaurantService.ManageOffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageOffersImpl implements ManageOffersService {

    @Autowired
    private ManageOffersRepository manageOffersRepository;
    @Override
    public List<ManageOffers> getAllOffers() {
        return manageOffersRepository.findAll();
    }

    @Override
    public ManageOffers saveOffers(ManageOffers Offer) {
        return manageOffersRepository.save(Offer);
    }

    @Override
    public ManageOffers getOfferByNo(Long No) {
        return manageOffersRepository.findById(No).get();
    }

    @Override
    public ManageOffers updateOffers(ManageOffers Offer) {
        return manageOffersRepository.save(Offer);
    }

    @Override
    public void deleteOffersByNo(Long No) {manageOffersRepository.deleteById(No);
    }

    @Override
    public Page<ManageOffers> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return manageOffersRepository.findAll(pageable);
    }
}
