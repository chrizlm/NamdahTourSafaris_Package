package com.chris.namdahtours_package.Service;

import com.chris.namdahtours_package.Model.TravelPackage;
import com.chris.namdahtours_package.Repository.TravelPackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TravelPackageServiceTest {

    @Mock private TravelPackageRepository travelPackageRepository;
    private TravelPackageService travelPackageServiceUnderTest;

    @BeforeEach
    void setUp() {
        travelPackageServiceUnderTest = new TravelPackageService(travelPackageRepository);
    }

    @Test
    void addPackage() {
        //given
        int id = 1;
        LocalDate date = LocalDate.of(2022, 1,1);
        TravelPackage travelPackage = new TravelPackage(
                id,
                "ndovu",
                3,
                date,
                1500.00

        );

        //when
        travelPackageServiceUnderTest.addPackage(travelPackage);

        //then
        ArgumentCaptor<TravelPackage> travelPackageArgumentCaptor = ArgumentCaptor.forClass(TravelPackage.class);
        verify(travelPackageRepository).save(travelPackageArgumentCaptor.capture());

        TravelPackage expectedTravelPackage = travelPackageArgumentCaptor.getValue();

        assertThat(expectedTravelPackage).isEqualTo(travelPackage);


    }

    @Test
    void getTravelPackage() throws InstanceNotFoundException {
        //given
        int id = 1;
        LocalDate date = LocalDate.of(2022, 1,1);
        TravelPackage travelPackage = new TravelPackage(
                id,
                "ndovu",
                3,
                date,
                1500.00

        );

        given(travelPackageRepository.findById(id)).willReturn(Optional.of(travelPackage));

        //when
        TravelPackage expectedTravelPackage = travelPackageServiceUnderTest.getTravelPackage(id);

        //then
        assertThat(expectedTravelPackage).isEqualTo(travelPackage);

    }

    @Test
    void getAllPackages() {
        //given
        //when
        travelPackageServiceUnderTest.getAllPackages();

        //then
        verify(travelPackageRepository).findAll();
    }

    @Test
    void updatePackage() throws InstanceNotFoundException {
        //given
        int id = 1;
        LocalDate date = LocalDate.of(2022, 1,1);
        LocalDate new_date = LocalDate.of(2022, 2,2);
        double new_amount = 2000.00;
        TravelPackage travelPackage = new TravelPackage(
                id,
                "ndovu",
                3,
                date,
                1500.00

        );

        given(travelPackageRepository.findById(id)).willReturn(Optional.of(travelPackage));

        //when
        travelPackageServiceUnderTest.updatePackage(id, new_date, new_amount);

        //then
        Optional<TravelPackage> expectedTravelPackage = travelPackageRepository.findById(id);
        assertThat(expectedTravelPackage.get().getTravel_date()).isEqualTo(new_date);
        assertThat(expectedTravelPackage.get().getAmount()).isEqualTo(new_amount);
        assertThat(expectedTravelPackage).isNotEqualTo(travelPackage);
    }

    @Test
    void deletePackage() throws InstanceNotFoundException {
        //given
        int id = 1;
        given(travelPackageRepository.existsById(id)).willReturn(true);

        //when
        travelPackageServiceUnderTest.deletePackage(id);

        //then
        verify(travelPackageRepository).deleteById(id);
    }

    @Test
    void willThrowExceptionDuringDelete() throws InstanceNotFoundException {
        //given
        int id = 1;
        given(travelPackageRepository.existsById(id)).willReturn(false);

        //when
        //then

        assertThatThrownBy(() -> travelPackageServiceUnderTest.deletePackage(id))
                .isInstanceOf(InstanceNotFoundException.class)
                .hasMessageContaining("package does not exist");

        verify(travelPackageRepository, never()).deleteById(id);
    }
}