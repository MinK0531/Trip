package com.mink.trip.country.repository;

import com.mink.trip.country.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    public List<Country> findAllByOrderByCountryNameAsc();

}
