package com.amit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.entity.TemporaryAddress;

public interface TemporaryAddressRepo extends JpaRepository<TemporaryAddress,Integer> {

}
