package com.exemplo.drogaria;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByRemedio(Remedio remedio);  // <- ESTE MÉTODO AQUI
}
