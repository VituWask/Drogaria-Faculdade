package com.exemplo.drogaria;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/vendas")  // Mapeia a URL base para /vendas
@CrossOrigin(origins = "http://localhost:3000") // Permite o Angular consumir a API
public class VendaController {

    private final VendaRepository repository;
    private final RemedioRepository remedioRepository;

    // Injeção de dependência dos dois repositórios
    public VendaController(VendaRepository repository, RemedioRepository remedioRepository) {
        this.repository = repository;
        this.remedioRepository = remedioRepository;
    }

    @GetMapping
    public List<Venda> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Venda criar(@RequestBody Venda venda) {
        // Valida se o remédio está presente
        if (venda.getRemedio() == null || venda.getRemedio().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Remédio não informado");
        }

        // Busca o remédio no banco
        Remedio remedio = remedioRepository.findById(venda.getRemedio().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Remédio não encontrado"));

        // Verifica estoque
        if (remedio.getQuantidadeEstoque() < venda.getQuantidade()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estoque insuficiente");
        }

        // Calcula valor total
        double valorTotal = remedio.getPreco() * venda.getQuantidade();
        venda.setValorTotal(valorTotal);

        // Atualiza o estoque
        remedio.setQuantidadeEstoque(remedio.getQuantidadeEstoque() - venda.getQuantidade());
        remedioRepository.save(remedio);

        // Garante que a venda use o objeto correto do banco
        venda.setRemedio(remedio);

        // Salva a venda
        return repository.save(venda);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada");
        }
        repository.deleteById(id);
    }
}
