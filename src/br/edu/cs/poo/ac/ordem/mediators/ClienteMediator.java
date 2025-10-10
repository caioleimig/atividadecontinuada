package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;

public class ClienteMediator {

    // --- Implementação do Padrão Singleton ---
    private static ClienteMediator instancia;

    private ClienteMediator() { }

    public static ClienteMediator getInstancia() {
        if (instancia == null) {
            instancia = new ClienteMediator();
        }
        return instancia;
    }

    // --- Simulação do DAO (Banco de Dados em Memória) ---
    private Map<String, Cliente> repositorio = new HashMap<>();

    // --- Métodos de Negócio (CRUD) ---

    public ResultadoMediator incluir(Cliente cliente) {
        ResultadoMediator resultadoValidacao = validar(cliente);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        if (repositorio.containsKey(cliente.getCpfCnpj())) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ já existente");
            return new ResultadoMediator(true, false, erros);
        }

        repositorio.put(cliente.getCpfCnpj(), cliente);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator alterar(Cliente cliente) {
        ResultadoMediator resultadoValidacao = validar(cliente);
        if (!resultadoValidacao.isValidado()) {
            return resultadoValidacao;
        }

        if (!repositorio.containsKey(cliente.getCpfCnpj())) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ inexistente");
            return new ResultadoMediator(true, false, erros);
        }

        repositorio.put(cliente.getCpfCnpj(), cliente);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator excluir(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ não informado");
            return new ResultadoMediator(false, false, erros);
        }

        if (!repositorio.containsKey(cpfCnpj)) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ inexistente");
            return new ResultadoMediator(true, false, erros);
        }

        repositorio.remove(cpfCnpj);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public Cliente buscar(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            return null;
        }
        return repositorio.get(cpfCnpj);
    }

    public ResultadoMediator validar(Cliente cliente) {
        ListaString mensagensErro = new ListaString();

        if (cliente == null) {
            mensagensErro.adicionar("Cliente não informado");
            return new ResultadoMediator(false, false, mensagensErro);
        }

        // Validação do CPF/CNPJ
        if (StringUtils.estaVazia(cliente.getCpfCnpj())) {
            mensagensErro.adicionar("CPF/CNPJ não informado");
        } else {
            ResultadoValidacaoCPFCNPJ resCpf = ValidadorCPFCNPJ.validarCPFCNPJ(cliente.getCpfCnpj());
            if (resCpf.getErroValidacao() != null) {
                switch (resCpf.getErroValidacao()) {
                    case CPF_CNPJ_NAO_E_CPF_NEM_CNPJ:
                        mensagensErro.adicionar("Não é CPF nem CNJP");
                        break;
                    case CPF_CNPJ_COM_DV_INVALIDO:
                        mensagensErro.adicionar("CPF ou CNPJ com dígito verificador inválido");
                        break;
                    default:
                        mensagensErro.adicionar("CPF/CNPJ inválido");
                        break;
                }
            }
        }

        // Validação do Nome
        if (StringUtils.estaVazia(cliente.getNome())) {
            mensagensErro.adicionar("Nome não informado");
        } else if (cliente.getNome().length() > 50) {
            mensagensErro.adicionar("Nome tem mais de 50 caracteres");
        }
        
        // Validação do Contato
        Contato contato = cliente.getContato();
        if (contato == null) {
            mensagensErro.adicionar("Contato não informado");
        } else {
            boolean emailVazio = StringUtils.estaVazia(contato.getEmail());
            boolean celularVazio = StringUtils.estaVazia(contato.getCelular());

            if (emailVazio && celularVazio) {
                mensagensErro.adicionar("Celular e e-mail não foram informados");
            } else {
                if (!emailVazio && !StringUtils.emailValido(contato.getEmail())) {
                    mensagensErro.adicionar("E-mail está em um formato inválido");
                }
                if (!celularVazio && !StringUtils.telefoneValido(contato.getCelular())) {
                    mensagensErro.adicionar("Celular está em um formato inválido");
                }
            }
            if (contato.isEhZap() && celularVazio) { // <<< AJUSTADO PARA USAR SUA CLASSE Contato
                 mensagensErro.adicionar("Celular não informado e indicador de zap ativo");
            }
        }
        
        // Validação da Data de Cadastro
        if (cliente.getDataCadastro() == null) {
            mensagensErro.adicionar("Data do cadastro não informada");
        } else if (cliente.getDataCadastro().isAfter(LocalDate.now())) {
            mensagensErro.adicionar("Data do cadastro não pode ser posterior à data atual");
        }

        boolean validado = (mensagensErro.tamanho() == 0); // <<< AJUSTADO PARA USAR SUA CLASSE ListaString
        return new ResultadoMediator(validado, false, mensagensErro);
    }
}