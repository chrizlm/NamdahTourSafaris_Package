package com.chris.namdahtours_package.Repository;

import com.chris.namdahtours_package.Model.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Integer> {
}
