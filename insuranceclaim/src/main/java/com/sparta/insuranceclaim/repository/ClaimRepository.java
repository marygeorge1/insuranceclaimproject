package com.sparta.insuranceclaim.repository;

import com.sparta.insuranceclaim.model.Claim;
import com.sparta.insuranceclaim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.thymeleaf.expression.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {
        List<Claim> findByUser(User user);

        List<Claim> findAllByClaimStatus(String status);

    List<Claim> findAllByReferenceId(String refferenceNo);
}