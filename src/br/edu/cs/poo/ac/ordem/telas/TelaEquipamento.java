package br.edu.cs.poo.ac.ordem.telas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.DadosEquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;

public class TelaEquipamento extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtSerial;
    private JComboBox<String> comboTipo;
    private JTextArea txtDescricao;
    private JRadioButton rdbtnNaoNovo, rdbtnSimNovo;
    private JRadioButton rdbtnNaoSensiveis, rdbtnSimSensiveis;
    private JRadioButton rdbtnNaoServidor, rdbtnSimServidor;
    private JFormattedTextField txtValorEstimado;
    private final ButtonGroup buttonGroupNovo = new ButtonGroup();
    private final ButtonGroup buttonGroupSensiveis = new ButtonGroup();
    private final ButtonGroup buttonGroupServidor = new ButtonGroup();
    private JPanel panelNotebook;
    private JPanel panelDesktop;

    private DadosEquipamentoMediator equipamentoMediator;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaEquipamento frame = new TelaEquipamento();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TelaEquipamento() {
        equipamentoMediator = new DadosEquipamentoMediator();

        setTitle("Cadastro de Equipamento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 500);
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

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(10, 28, 46, 14);
        panelAcesso.add(lblTipo);

        comboTipo = new JComboBox<>();
        comboTipo.setModel(new DefaultComboBoxModel<>(new String[] {"Notebook", "Desktop"}));
        comboTipo.setBounds(45, 24, 120, 22);
        panelAcesso.add(comboTipo);

        JLabel lblSerial = new JLabel("Serial:");
        lblSerial.setBounds(175, 28, 46, 14);
        panelAcesso.add(lblSerial);

        txtSerial = new JTextField();
        txtSerial.setBounds(215, 25, 170, 20);
        panelAcesso.add(txtSerial);
        txtSerial.setColumns(10);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(395, 24, 109, 23);
        panelAcesso.add(btnBuscar);
        
        // --- PAINEL DE DADOS ---
        JPanel panelDados = new JPanel();
        panelDados.setBorder(new TitledBorder(null, "Área de dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelDados.setBounds(10, 87, 514, 320);
        contentPane.add(panelDados);
        panelDados.setLayout(null);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(10, 25, 65, 14);
        panelDados.add(lblDescricao);
        
        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBounds(80, 22, 424, 60);
        panelDados.add(txtDescricao);

        JLabel lblNovo = new JLabel("É Novo?");
        lblNovo.setBounds(10, 100, 65, 14);
        panelDados.add(lblNovo);

        rdbtnNaoNovo = new JRadioButton("NÃO");
        buttonGroupNovo.add(rdbtnNaoNovo);
        rdbtnNaoNovo.setSelected(true);
        rdbtnNaoNovo.setBounds(80, 96, 60, 23);
        panelDados.add(rdbtnNaoNovo);

        rdbtnSimNovo = new JRadioButton("SIM");
        buttonGroupNovo.add(rdbtnSimNovo);
        rdbtnSimNovo.setBounds(140, 96, 60, 23);
        panelDados.add(rdbtnSimNovo);

        JLabel lblValorEstimado = new JLabel("Valor Estimado:");
        lblValorEstimado.setBounds(10, 135, 90, 14);
        panelDados.add(lblValorEstimado);
        
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        txtValorEstimado = new JFormattedTextField(formatter);
        txtValorEstimado.setBounds(100, 132, 130, 20);
        panelDados.add(txtValorEstimado);
        
        // Painel Condicional para Notebook
        panelNotebook = new JPanel();
        panelNotebook.setBorder(new TitledBorder(null, "Espec\u00EDfico para Notebook", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelNotebook.setBounds(10, 170, 494, 60);
        panelDados.add(panelNotebook);
        panelNotebook.setLayout(null);
        
        JLabel lblSensiveis = new JLabel("Carrega dados sensíveis?");
        lblSensiveis.setBounds(10, 25, 150, 14);
        panelNotebook.add(lblSensiveis);
        
        rdbtnNaoSensiveis = new JRadioButton("NÃO");
        buttonGroupSensiveis.add(rdbtnNaoSensiveis);
        rdbtnNaoSensiveis.setSelected(true);
        rdbtnNaoSensiveis.setBounds(160, 21, 60, 23);
        panelNotebook.add(rdbtnNaoSensiveis);
        
        rdbtnSimSensiveis = new JRadioButton("SIM");
        buttonGroupSensiveis.add(rdbtnSimSensiveis);
        rdbtnSimSensiveis.setBounds(220, 21, 60, 23);
        panelNotebook.add(rdbtnSimSensiveis);
        
        // Painel Condicional para Desktop
        panelDesktop = new JPanel();
        panelDesktop.setBorder(new TitledBorder(null, "Espec\u00EDfico para Desktop", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelDesktop.setBounds(10, 240, 494, 60);
        panelDados.add(panelDesktop);
        panelDesktop.setLayout(null);
        
        JLabel lblServidor = new JLabel("É Servidor?");
        lblServidor.setBounds(10, 25, 150, 14);
        panelDesktop.add(lblServidor);
        
        rdbtnNaoServidor = new JRadioButton("NÃO");
        buttonGroupServidor.add(rdbtnNaoServidor);
        rdbtnNaoServidor.setSelected(true);
        rdbtnNaoServidor.setBounds(160, 21, 60, 23);
        panelDesktop.add(rdbtnNaoServidor);
        
        rdbtnSimServidor = new JRadioButton("SIM");
        buttonGroupServidor.add(rdbtnSimServidor);
        rdbtnSimServidor.setBounds(220, 21, 60, 23);
        panelDesktop.add(rdbtnSimServidor);
        
        // --- PAINEL DE AÇÕES ---
        JPanel panelAcoes = new JPanel();
        panelAcoes.setBounds(10, 418, 514, 32);
        contentPane.add(panelAcoes);
        panelAcoes.setLayout(null);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(112, 5, 89, 23);
        panelAcoes.add(btnSalvar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(211, 5, 89, 23);
        panelAcoes.add(btnExcluir);
        
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(310, 5, 89, 23);
        panelAcoes.add(btnLimpar);

        // =============================================================================
        // CONFIGURAÇÃO DOS LISTENERS (AÇÕES DOS COMPONENTES)
        // =============================================================================

        // Listener do ComboBox para alternar a visibilidade dos painéis
        comboTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alternarPaineis();
            }
        });
        
        // Ação do botão SALVAR
        btnSalvar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String tipo = (String) comboTipo.getSelectedItem();
        		Equipamento equipamento;
        		
        		// Cria o objeto correto (Notebook ou Desktop)
        		if ("Notebook".equals(tipo)) {
        			Notebook notebook = new Notebook();
        			notebook.setCarregaDadosSensiveis(rdbtnSimSensiveis.isSelected());
        			equipamento = notebook;
        		} else {
        			Desktop desktop = new Desktop();
        			desktop.setEhServidor(rdbtnSimServidor.isSelected());
        			equipamento = desktop;
        		}
        		
        		// Preenche os dados comuns
        		equipamento.setSerial(txtSerial.getText());
        		equipamento.setDescricao(txtDescricao.getText());
        		equipamento.setEhNovo(rdbtnSimNovo.isSelected());
        		
        		if (txtValorEstimado.getValue() instanceof Number) {
                    equipamento.setValorEstimado(((Number) txtValorEstimado.getValue()).doubleValue());
                }
        		
        		ResultadoMediator resultado = equipamentoMediator.incluir(equipamento); // ou alterar
        		
        		if (resultado.isSucesso()) {
                    JOptionPane.showMessageDialog(TelaEquipamento.this, "Equipamento salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                } else {
                    String erros = String.join("\n", resultado.getMensagens());
                    JOptionPane.showMessageDialog(TelaEquipamento.this, "Ocorreram os seguintes erros:\n" + erros, "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                }
        	}
        });

        // Ação do botão LIMPAR
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        
        // Ação do botão BUSCAR (a implementar a lógica completa)
        btnBuscar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// Lógica de busca...
        		JOptionPane.showMessageDialog(TelaEquipamento.this, "Funcionalidade de busca a ser implementada.");
        	}
        });

        // Ação do botão EXCLUIR (a implementar a lógica completa)
        btnExcluir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// Lógica de exclusão...
        		JOptionPane.showMessageDialog(TelaEquipamento.this, "Funcionalidade de exclusão a ser implementada.");
        	}
        });
        
        // Garante que o estado inicial dos painéis esteja correto
        alternarPaineis();
    }
    
    /**
     * Alterna a visibilidade dos painéis de notebook e desktop com base na seleção do ComboBox.
     */
    private void alternarPaineis() {
        String selecionado = (String) comboTipo.getSelectedItem();
        if ("Notebook".equals(selecionado)) {
            panelNotebook.setVisible(true);
            panelDesktop.setVisible(false);
        } else {
            panelNotebook.setVisible(false);
            panelDesktop.setVisible(true);
        }
    }
    
    /**
     * Limpa todos os campos do formulário.
     */
    private void limparCampos() {
        comboTipo.setSelectedIndex(0);
        txtSerial.setText("");
        txtDescricao.setText("");
        rdbtnNaoNovo.setSelected(true);
        txtValorEstimado.setValue(null);
        rdbtnNaoSensiveis.setSelected(true);
        rdbtnNaoServidor.setSelected(true);
    }
}