package com.food.AdminSevice;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageCity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManageAreaService {

    List<ManageArea> getAllArea();

    ManageArea saveArea(ManageArea Area);

    ManageArea getAreaByNo(Long No);

    ManageArea updateArea(ManageArea Area);

    void deleteAreaByNo(Long No);

    Page<ManageArea> findPaginated(int pageNo, int pageSize);


}
