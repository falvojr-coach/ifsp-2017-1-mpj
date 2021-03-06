/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsp.view;

import br.edu.ifsp.controller.PermissaoController;
import br.edu.ifsp.controller.UsuarioController;
import br.edu.ifsp.model.Permissao;
import br.edu.ifsp.model.Usuario;
import br.edu.ifsp.util.DateUtil;
import br.edu.ifsp.util.ExcecaoNegocial;
import br.edu.ifsp.util.Mensagens;
import java.awt.Rectangle;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Classe responsável pelos elementos visuais da tela home.
 *
 * @author falvojr
 *
 * @see
 * http://www.kode-blog.com/java-swing-jdbc-crud-example-with-jasper-reports
 */
public class HomeJFrame extends javax.swing.JFrame {

    /**
     * Creates new form HomeJFrame
     */
    public HomeJFrame() {
        initComponents();
        carregarComponentes();
        configurarConponentes();
    }

    private void carregarComponentes() {
        try {
            this.carregarPermissoes();
            this.carregarUsuarios(false);
        } catch (ExcecaoNegocial excecao) {
            Mensagens.mostrarErro(this, excecao);
        }
    }

    private void carregarPermissoes() throws ExcecaoNegocial {
        List<Permissao> lista = PermissaoController.getInstancia().listar();
        cboPermissoes.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private void carregarUsuarios(boolean ehInsercao) throws ExcecaoNegocial {
        int linhaSelecionada = tblUsuarios.getSelectedRow();
        if (linhaSelecionada == -1) {
            linhaSelecionada = 0;
        }
        final List<Usuario> usuarios = UsuarioController.getInstancia().listar();
        if (tblUsuarios.getModel() instanceof UsuarioTableModel) {
            final UsuarioTableModel model = getUsuarioTableModel();
            model.setUsuarios(usuarios);
            model.fireTableDataChanged();
        } else {
            TableModel model = new UsuarioTableModel(usuarios);
            tblUsuarios.setModel(model);
        }

        if (ehInsercao) {
            linhaSelecionada = usuarios.size() - 1;
            this.moverScrollFim(usuarios);
        }
        if (!usuarios.isEmpty() && linhaSelecionada < usuarios.size()) {
            tblUsuarios.setRowSelectionInterval(linhaSelecionada, linhaSelecionada);
        } else {
            limparCampos();
        }
    }

    /**
     * @see http://stackoverflow.com/a/32942079/3072570
     */
    private void configurarConponentes() {
        tblUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                final int selectedRow = tblUsuarios.getSelectedRow();
                // Condicão necessaria para evitar click duplo.
                if (!event.getValueIsAdjusting() && selectedRow != -1) {
                    UsuarioTableModel model = getUsuarioTableModel();
                    Usuario usuario = model.getUsuarios().get(tblUsuarios.convertRowIndexToModel(selectedRow));
                    txtId.setText(usuario.getId().toString());
                    chkAtivo.setSelected(usuario.isAtivo());
                    txtEmail.setText(usuario.getEmail());
                    cboPermissoes.setSelectedItem(usuario.getPermissao());
                    txtSenha.setText(usuario.getSenha());
                }
            }
        });
        tblUsuarios.setAutoCreateRowSorter(true);
        this.ordenarPorId();
        this.configurarTelaCheia();
    }

    private void ordenarPorId() {
        // Ordena pela coluna ID
        tblUsuarios.getRowSorter().toggleSortOrder(0);
    }

    private void configurarTelaCheia() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private UsuarioTableModel getUsuarioTableModel() {
        return (UsuarioTableModel) tblUsuarios.getModel();
    }

    private void moverScrollFim(List<Usuario> usuarios) {
        // Move o scroll para o fim
        int ultimaLinha = usuarios.size() - 1;
        tblUsuarios.scrollRectToVisible(new Rectangle(tblUsuarios.getCellRect(ultimaLinha, 0, true)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTable = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        pnlUsuario = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblPermissao = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        lblExpiracao = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        chkAtivo = new javax.swing.JCheckBox();
        txtEmail = new javax.swing.JTextField();
        cboPermissoes = new javax.swing.JComboBox<>();
        txtSenha = new javax.swing.JPasswordField();
        txtExpiracao = new javax.swing.JFormattedTextField(DateUtil.BRASIL_FORMAT);
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(txtExpiracao);
        } catch (ParseException ex) { }
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerenciar Usuários");
        setExtendedState(6);

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pnlTable.setViewportView(tblUsuarios);

        pnlUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Detalhes do Usuário"));

        lblId.setText("ID:");

        lblEmail.setText("Email:");

        lblPermissao.setText("Permissão:");

        lblSenha.setText("Senha:");

        lblExpiracao.setText("Expiração:");

        txtId.setEditable(false);
        txtId.setEnabled(false);

        chkAtivo.setSelected(true);
        chkAtivo.setText("Ativo");

        javax.swing.GroupLayout pnlUsuarioLayout = new javax.swing.GroupLayout(pnlUsuario);
        pnlUsuario.setLayout(pnlUsuarioLayout);
        pnlUsuarioLayout.setHorizontalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblPermissao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblExpiracao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboPermissoes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEmail)
                    .addComponent(txtSenha)
                    .addGroup(pnlUsuarioLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(chkAtivo)
                        .addGap(0, 102, Short.MAX_VALUE))
                    .addComponent(txtExpiracao))
                .addContainerGap())
        );
        pnlUsuarioLayout.setVerticalGroup(
            pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId)
                    .addComponent(chkAtivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboPermissoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPermissao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExpiracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExpiracao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnDeletar.setText("Deletar");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnExportar.setText("Exportar");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExportar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeletar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExportar)
                .addGap(18, 18, 18)
                .addComponent(pnlUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeletar)
                    .addComponent(btnSalvar)
                    .addComponent(btnNovo))
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        Usuario usuario = new Usuario();
        final boolean ehInsercao = txtId.getText().isEmpty();
        if (!ehInsercao) {
            usuario.setId(Long.valueOf(txtId.getText()));
        }
        usuario.setAtivo(chkAtivo.isSelected());
        usuario.setEmail(txtEmail.getText());
        String senha = new String(txtSenha.getPassword());
        usuario.setSenha(senha);
        usuario.setPermissao((Permissao) cboPermissoes.getSelectedItem());
        //TODO Tratar campo txtExpiracao (talvez seja interessante usar o componente jcalendar)
        try {
            UsuarioController.getInstancia().salvar(usuario);
            this.carregarUsuarios(ehInsercao);
            JOptionPane.showMessageDialog(this, Mensagens.SUCESSO_USUARIO, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            txtId.setText(usuario.getId().toString());
        } catch (ExcecaoNegocial excecao) {
            Mensagens.mostrarErro(this, excecao);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void limparCampos() {
        txtId.setText("");
        chkAtivo.setSelected(true);
        txtEmail.setText("");
        cboPermissoes.setSelectedIndex(0);
        txtSenha.setText("");
        tblUsuarios.clearSelection();
    }

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        Usuario usuario = new Usuario();
        usuario.setId(Long.valueOf(txtId.getText()));
        try {
            String msg = String.format("Deseja realmente excluir %s?", txtEmail.getText());
            int resposta = JOptionPane.showConfirmDialog(this, msg, "Confirmacão", JOptionPane.YES_NO_OPTION);
            if (resposta == 0) {
                UsuarioController.getInstancia().excluir(usuario);
                this.carregarUsuarios(false);
                JOptionPane.showMessageDialog(this, Mensagens.SUCESSO_EXCLUSAO_USUARIO, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (ExcecaoNegocial excecao) {
            Mensagens.mostrarErro(this, excecao);
        }
    }//GEN-LAST:event_btnDeletarActionPerformed

    /**
     * @see http://www.k19.com.br/artigos/relatorios-em-java-jasperreports-e-irepor/
     */
    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try {
            final List<Usuario> usuarios = getUsuarioTableModel().getUsuarios();

            final JasperReport report = JasperCompileManager.compileReport("relatorios/usuarios.jrxml");
            final JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(usuarios));
            JasperExportManager.exportReportToPdfFile(print, "relatorios/usuarios.pdf");
            JOptionPane.showMessageDialog(this, Mensagens.SUCESSO_JR, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        } catch (JRException jasperException) {
            Mensagens.mostrarErro(this, new ExcecaoNegocial(Mensagens.ERRO_JR, jasperException));
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cboPermissoes;
    private javax.swing.JCheckBox chkAtivo;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExpiracao;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblPermissao;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JScrollPane pnlTable;
    private javax.swing.JPanel pnlUsuario;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JFormattedTextField txtExpiracao;
    private javax.swing.JTextField txtId;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables

}
