/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp_gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 *
 * @author alexandre
 */
public class GUI extends javax.swing.JFrame {
    private String usernameDestino;
    private static Ajuda ajuda;
    private static Sobre sobre;
    private static Contato contato;
    DefaultListModel listModel, listaModel2;

    //JList<String> jList1 = new JList<String>(new DefaultListModel<String>());




    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        listModel = new DefaultListModel();
        
        listaModel2 = new DefaultListModel();

        listaOnline.setModel(listModel);


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mensagemEditText = new javax.swing.JTextField();
        enviarBt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatWindow = new javax.swing.JTextArea();
        butaoPrivado = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaOnline = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mensagemEditText.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        mensagemEditText.setName("mensagemTextField"); // NOI18N

        enviarBt.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        enviarBt.setText("Enviar");
        enviarBt.setActionCommand("enviarBt");
        enviarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarBtActionPerformed(evt);
            }
        });
        enviarBt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Enter(evt);
            }
        });

        chatWindow.setColumns(20);
        chatWindow.setRows(5);
        chatWindow.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        chatWindow.setEnabled(false);
        jScrollPane1.setViewportView(chatWindow);

        butaoPrivado.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        butaoPrivado.setText("Privado");

        listaOnline.setBackground(new java.awt.Color(227, 224, 224));
        listaOnline.setFont(new java.awt.Font("Lucida Grande", 2, 12)); // NOI18N
        listaOnline.setForeground(new java.awt.Color(0, 204, 102));
        jScrollPane2.setViewportView(listaOnline);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 102));
        jLabel1.setText("Online");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/logo_aplicativo_pequeno.png"))); // NOI18N

        jMenu1.setText("Menu");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Ajuda");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Sobre");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Contato");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Sair");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addComponent(mensagemEditText))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(butaoPrivado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(enviarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(butaoPrivado, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mensagemEditText)
                    .addComponent(enviarBt, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enviarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarBtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enviarBtActionPerformed

    private void Enter(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Enter
        // TODO add your handling code here:
           //
        
    }//GEN-LAST:event_Enter

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        ajuda = new Ajuda();
        ajuda.pack();
        ajuda.setLocationRelativeTo(null);
        ajuda.setTitle("Ajuda");
        ajuda.setDefaultCloseOperation(ajuda.DISPOSE_ON_CLOSE);
        ajuda.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        sobre = new Sobre();
        sobre.pack();
        sobre.setLocationRelativeTo(null);
        sobre.setTitle("Sobre");
        sobre.setDefaultCloseOperation(ajuda.DISPOSE_ON_CLOSE);
        sobre.setVisible(true);   // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
                // TODO add your handling code here:
        setVisible(false); //you can't see me!
        dispose(); //Destroy the JFrame object
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        contato = new Contato();
        contato.pack();
        contato.setLocationRelativeTo(null);
        contato.setTitle("Contato");
        contato.setDefaultCloseOperation(contato.DISPOSE_ON_CLOSE);
        contato.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    
    public void addEnviarListener(ActionListener e){
        this.enviarBt.addActionListener(e);
    }
   
    public void addPrivadoListener(ActionListener listen){
        butaoPrivado.addActionListener(listen);
    }
    
    public javax.swing.JTextArea getChatWindow(){
        return this.chatWindow;
    }
    
    public String getMensagem(){
        String retorno;
        retorno = this.mensagemEditText.getText();
        this.mensagemEditText.setText("");
        return retorno;
    }
    public javax.swing.JTextField getMensagem2(){
        return this.mensagemEditText;
    }
    
    public void mostrarErro(String erro){
        JOptionPane.showMessageDialog(null, erro, "Aviso!", JOptionPane.ERROR_MESSAGE);
    }
    public void mostrarAviso(String erro){
        JOptionPane.showMessageDialog(null, erro, "Aviso!", JOptionPane.WARNING_MESSAGE);
    }
    public void setUsername(String user){
        this.usernameDestino = user;
    }
    public void removeAllLista(){
        //((DefaultListModel)jList1.getModel()).addElement(user);
        listModel.removeAllElements();
        //JList list = new JList(listModel);
    }
    public void setUserLista(String user){
        //((DefaultListModel)jList1.getModel()).addElement(user);
        listModel.addElement(user);
        //JList list = new JList(listModel);
    }
    public javax.swing.JButton getEnviarBt(){
        return this.enviarBt;
    }
    public void setJlabel(String user){
        listaOnline.removeAll();;
        listaModel2.addElement(user);
        listaOnline.setModel(listaModel2);
        listaOnline.setForeground(Color.red);
        this.jLabel1.setText("PRIVADO");
        this.jLabel1.setForeground(Color.red);
    }
    public String getUsername(){
        return this.usernameDestino;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butaoPrivado;
    private javax.swing.JTextArea chatWindow;
    private javax.swing.JButton enviarBt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listaOnline;
    private javax.swing.JTextField mensagemEditText;
    // End of variables declaration//GEN-END:variables
}
