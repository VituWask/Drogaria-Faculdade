package com.exemplo.drogaria;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataVenda;       // Data da venda
    private Integer quantidade;   // Quantidade de medicamentos vendidos
    private Double valorTotal;    // Valor total da venda

    @ManyToOne
    @JoinColumn(name = "remedio_id", nullable = true) // Permite que o campo seja nulo após desvinculação
    private Remedio remedio;

    // Construtores
    public Venda() {}

    public Venda(Date dataVenda, Integer quantidade, Double valorTotal, Remedio remedio) {
        this.dataVenda = dataVenda;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.remedio = remedio;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Remedio getRemedio() {
        return remedio;
    }

    public void setRemedio(Remedio remedio) {
        this.remedio = remedio;
    }
}
