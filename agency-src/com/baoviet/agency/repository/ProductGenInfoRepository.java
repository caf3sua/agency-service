package com.baoviet.agency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baoviet.agency.domain.ProductGenInfo;


@Repository
public interface ProductGenInfoRepository extends JpaRepository<ProductGenInfo, String>, ProductGenInfoRepositoryExtend {
}