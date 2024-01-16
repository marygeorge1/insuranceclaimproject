package com.sparta.insuranceclaim.repository;

import com.sparta.insuranceclaim.model.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, Integer> {
}