package com.example.springsecurity.Repositories;

import com.example.springsecurity.Entites.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<product,Long> {



}
