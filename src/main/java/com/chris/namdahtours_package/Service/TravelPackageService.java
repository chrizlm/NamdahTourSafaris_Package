package com.chris.namdahtours_package.Service;

import com.chris.namdahtours_package.Model.TravelPackage;
import com.chris.namdahtours_package.Repository.TravelPackageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class TravelPackageService {
    private TravelPackageRepository travelPackageRepository;

    @Autowired
    public TravelPackageService(TravelPackageRepository travelPackageRepository) {
        this.travelPackageRepository = travelPackageRepository;
    }

    /**
     * this class will perform
     * CRUD operation
     * Getting package(s)
     */

    //CREATE
    public String addPackage(TravelPackage travelPackage){
        travelPackageRepository.save(travelPackage);
        log.info("package added");
        return "Travel package saved";
    }


    //GET PACKAGE
    public TravelPackage getTravelPackage(int id) throws InstanceNotFoundException {
        log.info("getting package with id: ?", id);
        return travelPackageRepository.findById(id)
                .orElseThrow(() -> new InstanceNotFoundException("package not found"));
    }


    //GET ALL PACKAGES
    public List<TravelPackage> getAllPackages(){
        log.info("getting all packages");
        return travelPackageRepository.findAll();
    }


    //UPDATE PACKAGE
    public String updatePackage(int id, LocalDate travel_date, double amount) throws InstanceNotFoundException {
        TravelPackage travelPackage = travelPackageRepository.findById(id)
                .orElseThrow(()-> new InstanceNotFoundException("package not found"));

        if(travel_date != null){
            travelPackage.setTravel_date(travel_date);
        }
        if(amount > 0){
            travelPackage.setAmount(amount);
        }

        travelPackageRepository.save(travelPackage);
        log.info("travel package updated");
        return "Travel package updated";

    }


    //DELETE PACKAGE
    public String deletePackage(int id) throws InstanceNotFoundException {
        boolean isPackagePresent = travelPackageRepository.existsById(id);
        if(!isPackagePresent){
            log.error("package with id: ? does not exists", id);
            throw new InstanceNotFoundException("package does not exist");
        }else{
            travelPackageRepository.deleteById(id);
            log.info("package deleted");
        }

        return "Package deleted";
    }
}
