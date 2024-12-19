package com.food.RestaurantService;

import com.food.RestaurantEntity.ManageOffers;
import com.food.RestaurantEntity.ManageProduct;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManageOffersService {

    List<ManageOffers> getAllOffers();

    ManageOffers saveOffers(ManageOffers Offer);


    ManageOffers getOfferByNo(Long No);

    ManageOffers updateOffers(ManageOffers Offer);

    void deleteOffersByNo(Long No);

    Page<ManageOffers> findPaginated(int pageNo, int pageSize);

}
