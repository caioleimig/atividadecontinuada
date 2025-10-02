package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrdemServico {
    private Cliente cliente;
    private PrecoBase precoBase;
    private Notebook notebook;
    private Desktop desktop;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;

    public LocalDate getDataEstimadaEntrega() {
        return dataHoraAbertura.toLocalDate().plusDays(prazoEmDias);
    }

    public String getNumero() {
        String tipo = notebook != null ? notebook.getIdTipo() : desktop.getIdTipo();
        String data = String.format("%04d%02d%02d%02d%02d",
                dataHoraAbertura.getYear(),
                dataHoraAbertura.getMonthValue(),
                dataHoraAbertura.getDayOfMonth(),
                dataHoraAbertura.getHour(),
                dataHoraAbertura.getMinute());

        String cpfCnpj = cliente.getCpfCnpj();
        if (cpfCnpj.length() == 14) { // CNPJ
            return tipo + data + cpfCnpj;
        } else { // CPF
            return dataHoraAbertura.getMonthValue() +
                   "" + dataHoraAbertura.getYear() +
                   "" + dataHoraAbertura.getDayOfMonth() +
                   "" + dataHoraAbertura.getHour() +
                   "" + dataHoraAbertura.getMinute() +
                   "000" + cpfCnpj;
        }
    }
}
