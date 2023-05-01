package com.example.marketplace.repository;

import com.example.marketplace.entities.IPlocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPLocationRepository extends JpaRepository<IPlocation,Long> {

    IPlocation findByIpAddress(String ip);
}
