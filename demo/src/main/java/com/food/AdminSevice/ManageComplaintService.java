package com.food.AdminSevice;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageComplaint;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ManageComplaintService {


    List<ManageComplaint> getAllComplaints();

    ManageComplaint saveComplaints(ManageComplaint Complaint);


    ManageComplaint getComplaintByNo(Long No);

    ManageComplaint updateComplaints(ManageComplaint Complaint);

    void deleteComplaintsByNo(Long No);

    Page<ManageComplaint> findPaginated(int pageNo, int pageSize);


}
