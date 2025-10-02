package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import java.time.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Cliente {
    private String cpfCnpj;
    private String nome;
    private Contato contato;
    private LocalDate dataCadastro;

    public int getIdadeCadastro() {
        return Period.between(dataCadastro, LocalDate.now()).getYears();
    }
}