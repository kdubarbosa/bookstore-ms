package com.example.bookstore_ms.domain.repository;

import com.example.bookstore_ms.domain.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

}
