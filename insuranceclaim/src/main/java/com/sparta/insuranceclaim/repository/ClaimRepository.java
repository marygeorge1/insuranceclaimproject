package com.sparta.insuranceclaim.repository;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
        List<Claim> findByUser(User user);

        List<Claim> findAllByClaimStatus(String status);
}