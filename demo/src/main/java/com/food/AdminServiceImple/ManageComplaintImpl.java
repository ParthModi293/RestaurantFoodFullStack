package com.food.AdminServiceImple;

import com.food.AdminEntity.ManageArea;
import com.food.AdminEntity.ManageComplaint;
import com.food.AdminRepository.ManageComplaintRepository;
import com.food.AdminSevice.ManageComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageComplaintImpl implements ManageComplaintService {

    @Autowired
    private ManageComplaintRepository manageComplaintRepository;


    @Override
    public List<ManageComplaint> getAllComplaints() {
        return manageComplaintRepository.findAll();
    }

    @Override
    public ManageComplaint saveComplaints(ManageComplaint Complaint) {
        return manageComplaintRepository.save(Complaint);
    }

    @Override
    public ManageComplaint getComplaintByNo(Long No) {
        return manageComplaintRepository.findById(No).get();
    }

    @Override
    public ManageComplaint updateComplaints(ManageComplaint Complaint) {
        return manageComplaintRepository.save(Complaint);
    }

    @Override
    public void deleteComplaintsByNo(Long No) {
manageComplaintRepository.deleteById(No);
    }


    @Override
    public Page<ManageComplaint> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return manageComplaintRepository.findAll(pageable);
    }


}
