package com.amit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.entity.PermanentAddress;

public interface PermanentAddressRepo  extends JpaRepository<PermanentAddress, Integer>{

}
