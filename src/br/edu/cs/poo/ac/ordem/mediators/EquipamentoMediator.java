package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entities.Desktop;
import br.edu.cs.poo.ac.ordem.entities.Notebook;
import br.edu.cs.poo.ac.utils.ListaString;

public class EquipamentoMediator {

    private static EquipamentoMediator instancia;
    private DesktopDAO desktopDAO;
    private NotebookDAO notebookDAO;

    private EquipamentoMediator() {
        desktopDAO = new DesktopDAO();
        notebookDAO = new NotebookDAO();
    }

    public static EquipamentoMediator getInstancia() {
        if (instancia == null) {
            instancia = new EquipamentoMediator();
        }
        return instancia;
    }

    public ResultadoMediator incluirDesktop(Desktop desk) {
        ResultadoMediator resultadoValidacao = validarDesktop(desk);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(false, false, resultadoValidacao.getMensagensErro());
        }

        boolean operacao = desktopDAO.incluir(desk);
        ListaString mensagens = new ListaString();
        if (!operacao) mensagens.adicionar("Erro ao incluir desktop.");

        return new ResultadoMediator(true, operacao, mensagens);
    }

    public ResultadoMediator alterarDesktop(Desktop desk) {
        ResultadoMediator resultadoValidacao = validarDesktop(desk);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(false, false, resultadoValidacao.getMensagensErro());
        }

        boolean operacao = desktopDAO.alterar(desk);
        ListaString mensagens = new ListaString();
        if (!operacao) mensagens.adicionar("Erro ao alterar desktop.");

        return new ResultadoMediator(true, operacao, mensagens);
    }

    public ResultadoMediator excluirDesktop(String idTipoSerial) {
        boolean operacao = desktopDAO.excluir(idTipoSerial);
        ListaString mensagens = new ListaString();
        if (!operacao) mensagens.adicionar("Desktop não encontrado.");
        return new ResultadoMediator(true, operacao, mensagens);
    }

    public Desktop buscarDesktop(String idTipoSerial) {
        return desktopDAO.buscar(idTipoSerial);
    }

    public ResultadoMediator validarDesktop(Desktop desk) {
        DadosEquipamento dados = new DadosEquipamento(
                desk.getSerial(), desk.getDescricao(), desk.isEhNovo(), desk.getValorEstimado()
        );
        return validar(dados);
    }

    public ResultadoMediator incluirNotebook(Notebook note) {
        ResultadoMediator resultadoValidacao = validarNotebook(note);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(false, false, resultadoValidacao.getMensagensErro());
        }

        boolean operacao = notebookDAO.incluir(note);
        ListaString mensagens = new ListaString();
        if (!operacao) mensagens.adicionar("Erro ao incluir notebook.");

        return new ResultadoMediator(true, operacao, mensagens);
    }

    public ResultadoMediator alterarNotebook(Notebook note) {
        ResultadoMediator resultadoValidacao = validarNotebook(note);
        if (!resultadoValidacao.isValidado()) {
            return new ResultadoMediator(false, false, resultadoValidacao.getMensagensErro());
        }

        boolean operacao = notebookDAO.alterar(note);
        ListaString mensagens = new ListaString();
        if (!operacao) mensagens.adicionar("Erro ao alterar notebook.");

        return new ResultadoMediator(true, operacao, mensagens);
    }

    public ResultadoMediator excluirNotebook(String idTipoSerial) {
        boolean operacao = notebookDAO.excluir(idTipoSerial);
        ListaString mensagens = new ListaString();
        if (!operacao) mensagens.adicionar("Notebook não encontrado.");
        return new ResultadoMediator(true, operacao, mensagens);
    }

    public Notebook buscarNotebook(String idTipoSerial) {
        return notebookDAO.buscar(idTipoSerial);
    }

    public ResultadoMediator validarNotebook(Notebook note) {
        DadosEquipamento dados = new DadosEquipamento(
                note.getSerial(), note.getDescricao(), note.isEhNovo(), note.getValorEstimado()
        );
        return validar(dados);
    }

    public ResultadoMediator validar(DadosEquipamento equip) {
        ListaString mensagens = new ListaString();
        boolean validado = true;

        if (equip.getSerial() == null || equip.getSerial().isEmpty()) {
            mensagens.adicionar("Serial inválido.");
            validado = false;
        }
        if (equip.getDescricao() == null || equip.getDescricao().isEmpty()) {
            mensagens.adicionar("Descrição inválida.");
            validado = false;
        }
        if (equip.getValorEstimado() <= 0) {
            mensagens.adicionar("Valor estimado deve ser positivo.");
            validado = false;
        }

        return new ResultadoMediator(validado, false, mensagens);
    }
}