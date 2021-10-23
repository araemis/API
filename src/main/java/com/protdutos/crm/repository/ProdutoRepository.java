package com.protdutos.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.protdutos.crm.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
