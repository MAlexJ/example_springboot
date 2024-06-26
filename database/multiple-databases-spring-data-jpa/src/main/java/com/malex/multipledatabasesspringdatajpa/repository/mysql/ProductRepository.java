package com.malex.multipledatabasesspringdatajpa.repository.mysql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malex.multipledatabasesspringdatajpa.entity.mysql.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {}
