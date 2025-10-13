package br.edu.cs.poo.ac.ordem.telas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;

public class TelaCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtCpfCnpj;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtCelular;
    private JCheckBox chkEhZap;
    private JFormattedTextField txtDataCadastro;

    // Mediator para fazer a ponte com a lógica de negócio
    private ClienteMediator clienteMediator;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaCliente frame = new TelaCliente();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public TelaCliente() {
        // Instancia o mediator assim que a tela é criada
        clienteMediator = new ClienteMediator();

        setTitle("Cadastro de Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 420);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // --- PAINEL DE ACESSO ---
        JPanel panelAcesso = new JPanel();
        panelAcesso.setBorder(new TitledBorder(null, "Área de acesso", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelAcesso.setBounds(10, 11, 514, 65);
        contentPane.add(panelAcesso);
        panelAcesso.setLayout(null);

        JLabel lblCpfCnpj = new JLabel("CPF ou CNPJ:");
        lblCpfCnpj.setBounds(10, 28, 86, 14);
        panelAcesso.add(lblCpfCnpj);

        txtCpfCnpj = new JTextField();
        txtCpfCnpj.setBounds(95, 25, 218, 20);
        panelAcesso.add(txtCpfCnpj);
        txtCpfCnpj.setColumns(10);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(323, 24, 89, 23);
        panelAcesso.add(btnBuscar);
        
        JButton btnNovo = new JButton("Novo");
        btnNovo.setBounds(418, 24, 86, 23);
        panelAcesso.add(btnNovo);

        // --- PAINEL DE DADOS ---
        JPanel panelDados = new JPanel();
        panelDados.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Área de dados", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelDados.setBounds(10, 87, 514, 230);
        contentPane.add(panelDados);
        panelDados.setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 30, 46, 14);
        panelDados.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(110, 27, 394, 20);
        panelDados.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(10, 70, 46, 14);
        panelDados.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(110, 67, 394, 20);
        panelDados.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblCelular = new JLabel("Celular:");
        lblCelular.setBounds(10, 110, 46, 14);
        panelDados.add(lblCelular);

        txtCelular = new JTextField();
        txtCelular.setBounds(110, 107, 150, 20);
        panelDados.add(txtCelular);
        txtCelular.setColumns(10);
        
        chkEhZap = new JCheckBox("É ZAP?");
        chkEhZap.setBounds(270, 106, 97, 23);
        panelDados.add(chkEhZap);

        JLabel lblDataCadastro = new JLabel("Data do Cadastro:");
        lblDataCadastro.setBounds(10, 150, 101, 14);
        panelDados.add(lblDataCadastro);
        
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            txtDataCadastro = new JFormattedTextField(mascaraData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtDataCadastro.setBounds(110, 147, 150, 20);
        panelDados.add(txtDataCadastro);

        // --- PAINEL DE AÇÕES ---
        JPanel panelAcoes = new JPanel();
        panelAcoes.setBounds(10, 328, 514, 42);
        contentPane.add(panelAcoes);
        panelAcoes.setLayout(null);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(112, 11, 89, 23);
        panelAcoes.add(btnSalvar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(211, 11, 89, 23);
        panelAcoes.add(btnExcluir);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(310, 11, 89, 23);
        panelAcoes.add(btnLimpar);

        // =============================================================================
        // CONFIGURAÇÃO DOS LISTENERS (AÇÕES DOS COMPONENTES)
        // =============================================================================
        
        // Listener para formatar o campo CPF/CNPJ quando ele perde o foco
        txtCpfCnpj.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = txtCpfCnpj.getText().replaceAll("[^0-9]", ""); // Remove tudo que não for número
                if (texto.length() == 11) {
                    texto = texto.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
                    txtCpfCnpj.setText(texto);
                } else if (texto.length() == 14) {
                    texto = texto.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
                    txtCpfCnpj.setText(texto);
                }
            }
        });
        
        // Ação do botão SALVAR
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 1. Coletar os dados da tela
                String cpfCnpj = txtCpfCnpj.getText().replaceAll("[^0-9]", "");
                
                Cliente cliente = new Cliente();
                cliente.setCpfCnpj(cpfCnpj);
                cliente.setNome(txtNome.getText());
                cliente.setDataCadastro(txtDataCadastro.getText()); // Assumindo que o mediator trata a conversão da data
                
                Contato contato = new Contato();
                contato.setEmail(txtEmail.getText());
                contato.setCelular(txtCelular.getText());
                contato.setEhZap(chkEhZap.isSelected());
                cliente.setContato(contato); // Assumindo que Cliente tem um método setContato
                
                // 2. Chamar o mediator
                // Aqui você precisaria de uma lógica para saber se é inclusão ou alteração.
                // Por exemplo, uma flag "modoEdicao" que fica true após uma busca.
                ResultadoMediator resultado = clienteMediator.incluir(cliente); // Ou clienteMediator.alterar(cliente);
                
                // 3. Exibir feedback
                if (resultado.isSucesso()) {
                    JOptionPane.showMessageDialog(TelaCliente.this, "Operação realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                } else {
                    // Monta a lista de erros para exibição
                    String erros = String.join("\n", resultado.getMensagens());
                    JOptionPane.showMessageDialog(TelaCliente.this, "Ocorreram os seguintes erros:\n" + erros, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Ação do botão BUSCAR
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cpfCnpj = txtCpfCnpj.getText().replaceAll("[^0-9]", "");
                
                ResultadoMediator resultado = clienteMediator.buscar(cpfCnpj);
                
                if (resultado.isSucesso()) {
                    Cliente clienteEncontrado = (Cliente) resultado.getEntidade();
                    preencherFormulario(clienteEncontrado);
                } else {
                    JOptionPane.showMessageDialog(TelaCliente.this, "Cliente não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Ação do botão EXCLUIR
        btnExcluir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		int confirm = JOptionPane.showConfirmDialog(TelaCliente.this, "Tem certeza que deseja excluir este cliente?", "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    String cpfCnpj = txtCpfCnpj.getText().replaceAll("[^0-9]", "");
                    ResultadoMediator resultado = clienteMediator.excluir(cpfCnpj);
                    
                    if (resultado.isSucesso()) {
                        JOptionPane.showMessageDialog(TelaCliente.this, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        limparCampos();
                    } else {
                        String erros = String.join("\n", resultado.getMensagens());
                        JOptionPane.showMessageDialog(TelaCliente.this, "Não foi possível excluir:\n" + erros, "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
        	}
        });

        // Ação do botão LIMPAR/NOVO
        ActionListener limparListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        };
        btnLimpar.addActionListener(limparListener);
        btnNovo.addActionListener(limparListener);
    }
    
    /**
     * Limpa todos os campos do formulário, retornando ao estado inicial.
     */
    private void limparCampos() {
        txtCpfCnpj.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        txtDataCadastro.setValue(null); // Limpa o campo formatado
        chkEhZap.setSelected(false);
        txtCpfCnpj.requestFocus(); // Põe o foco no primeiro campo
    }
    
    /**
     * Preenche o formulário com os dados de um Cliente encontrado.
     * @param cliente O cliente cujos dados serão exibidos.
     */
    private void preencherFormulario(Cliente cliente) {
        txtCpfCnpj.setText(cliente.getCpfCnpj()); // O ideal é que a máscara seja aplicada aqui também
        txtCpfCnpj.getFocusListeners()[0].focusLost(null); // Força a formatação
        
        txtNome.setText(cliente.getNome());
        txtDataCadastro.setText(cliente.getDataCadastro());
        
        if (cliente.getContato() != null) {
            txtEmail.setText(cliente.getContato().getEmail());
            txtCelular.setText(cliente.getContato().getCelular());
            chkEhZap.setSelected(cliente.getContato().isEhZap());
        }
    }
}