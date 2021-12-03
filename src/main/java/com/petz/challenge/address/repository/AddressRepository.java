package com.petz.challenge.address.repository;

import com.petz.challenge.address.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {
    Optional<Address> findFirstByLatitudeAndLongitude(BigDecimal latitude, BigDecimal longitude);
}
