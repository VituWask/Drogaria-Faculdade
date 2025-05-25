package com.exemplo.drogaria;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/remedios")
@CrossOrigin(origins = "http://localhost:3000") // libera o Angular para consumir a API
public class RemedioController {

    private final RemedioRepository remedioRepository;
    private final VendaRepository vendaRepository;

    public RemedioController(RemedioRepository remedioRepository, VendaRepository vendaRepository) {
        this.remedioRepository = remedioRepository;
        this.vendaRepository = vendaRepository;
    }

    @GetMapping
    public List<Remedio> listar() {
        return remedioRepository.findAll();
    }

    @PostMapping
    public Remedio criar(@RequestBody Remedio remedio) {
        return remedioRepository.save(remedio);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        // Verifica se o remédio existe
        Remedio remedio = remedioRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remédio não encontrado"));

        // Busca todas as vendas associadas a esse remédio
        List<Venda> vendas = vendaRepository.findByRemedio(remedio);

        // Remove a referência ao remédio em cada venda
        for (Venda venda : vendas) {
            venda.setRemedio(null);
            vendaRepository.save(venda);
        }

        // Agora pode deletar o remédio
        remedioRepository.deleteById(id);
    }
}
