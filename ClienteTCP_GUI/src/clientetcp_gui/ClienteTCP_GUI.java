/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp_gui;

import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexandre
 */
public class ClienteTCP_GUI{
    private static loginGUI loginView;
    private static Privado TelaPrivado;
    private static GUI chatView;
    private static ArrayList<GUI> chatsPrivados;
    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static String username;
    public static String separador;
    public static String identificador;
    public static String chaveGrupo, chavePrivado, chaveLista;
    public int flag=0;

    /**
     * @param args the command line arguments
     */
    public ClienteTCP_GUI() {
        chatsPrivados = new ArrayList<>();
        
        chaveGrupo = "876527493826";
        chavePrivado = "412432423421";
        chaveLista = "398776778392";
        identificador = "860742707452";
        separador = "742509754578";
        
        loginView = new loginGUI();
        loginView.addConectarListener(new ConnectListener());
        loginView.pack();
        loginView.setLocationRelativeTo(null);
        loginView.setTitle("Login");
        loginView.setVisible(true);
        
        chatView = new GUI();
        
        //chatView.addEnviarListener(new EnviarListener());
        chatView.getMensagem2().addKeyListener(new EnterListener(chatView));
                
        chatView.addEnviarListener(new EnviarListener(chaveGrupo, "", chatView));
        chatView.addPrivadoListener(new PrivadoListener());
        chatView.pack();
        chatView.setLocationRelativeTo(null);

        
        TelaPrivado = new Privado();
        TelaPrivado.addIniciarListener(new PrivadoListenerInterno());
        TelaPrivado.pack();
        TelaPrivado.setLocationRelativeTo(null);
        TelaPrivado.setTitle("Privado");
        TelaPrivado.setDefaultCloseOperation(TelaPrivado.DISPOSE_ON_CLOSE);


    }
    
    public static void main(String[] args) {
        ClienteTCP_GUI c = new ClienteTCP_GUI();
    }
    
    class ConnectListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //abrir o socket com o host dado
                int teste = loginView.getUserName().indexOf('<');//ver se esse caractere esta no nome. retorna >=0 se sim
                if (loginView.getUserName().contains("<>") || teste>=0){
                    loginView.mostrarErro("\tUsuário inválido!\n Por favor Remova '<>' do seu nome");
                }
                else{
                socket = new Socket(loginView.getHost(), loginView.getPorta());
                //setar username
                username = loginView.getUserName();
                //inicializar entrada e saida do socket
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                //enviar username
                out.println(username);
                System.out.println(username);
                //esconder tela de login
                loginView.setVisible(false);
                //mostrar tela de chat
                chatView.setTitle(username);

                chatView.setVisible(true);
                //iniciar thread para checar mensagens que possam ter chegado
                new Thread(new ReceberMsg()).start();
                }
            } catch (IOException ex) {
                loginView.mostrarErro("Não foi possível se conectar ao host nesta porta.");
            }
            
        }
    }
    
    class EnviarListener implements ActionListener{
        String msgEnviar, chave, destino;
        GUI chat;
        public EnviarListener(String chave, String destino, GUI chat){
            this.chave = chave;
            this.destino = destino;
            this.chat = chat;
        }

        private EnviarListener() {
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            msgEnviar = chat.getMensagem();
            //se a chave for de grupo, codificar desse jeito
            if(this.chave.equals(chaveGrupo)){
                out.println(chaveGrupo+separador+msgEnviar);
            //se for chave privada, codificar assim
            }else if(this.chave.equals(chavePrivado)){
                chat.getChatWindow().append(username+": "+msgEnviar+"\n");
                out.println(chavePrivado+separador+this.destino+identificador+msgEnviar);
            }
            //mantém foco na área da mensagem
            this.chat.getMensagem2().requestFocus();
        }
    }
    
    
    class PrivadoListenerInterno implements ActionListener{
        String parceiro;
        @Override
        public void actionPerformed(ActionEvent e) {
            parceiro = TelaPrivado.getParceiro();
            TelaPrivado.setVisible(false);

            //out.println("412432423421"+"-"+parceiro+"#"+"Ola amigo(a)!");//codigo para chat provado
            GUI novoChatPrivado = new GUI();
            novoChatPrivado.addEnviarListener(new EnviarListener(chavePrivado, parceiro, novoChatPrivado));
            novoChatPrivado.addPrivadoListener(new PrivadoListener());
            novoChatPrivado.getMensagem2().addKeyListener(new EnterListener(novoChatPrivado));
            novoChatPrivado.pack();
            novoChatPrivado.setTitle("Privado com "+parceiro);
            novoChatPrivado.setUsername(parceiro);
            novoChatPrivado.setDefaultCloseOperation(novoChatPrivado.DISPOSE_ON_CLOSE);
            novoChatPrivado.setJlabel(parceiro);
            novoChatPrivado.setVisible(true);
            chatsPrivados.add(novoChatPrivado);
        }
    }
    
    class PrivadoListener implements ActionListener{
        //String msgEnviar;
        @Override
        public void actionPerformed(ActionEvent e) {
            //msgEnviar = chatView.getMensagem();
            //out.println(msgEnviar);
            if(flag == 0){
                chatView.mostrarErro("Lista de usuários vazia!");
            }
            else if(TelaPrivado.isVazio())
                chatView.mostrarAviso("Somente você está conectado!");
            else{    
            TelaPrivado.setVisible(true);
            }
        }
    }  
    
    class ReceberMsg extends Thread{
        boolean scrollGrupo = true;
        @Override
        public void run(){
            while(true){

                try{
                   if(in.ready()){
                       String mensagem = in.readLine();
                       String[] parts = mensagem.split(separador);//separa em codigo e ip
                       String codigo = parts[0];
                       String corpo = parts[1];
                       if(codigo.equals(chaveLista)){
                           flag = 1;//usado pra
                           //atualiza a lista de clientes conecatos e muda na tela privado
                           if(!TelaPrivado.isVisible()){
                           TelaPrivado.limparLista();
                           chatView.removeAllLista();}
                           ArrayList<String> listaClientes;
                           listaClientes = new ArrayList<>();
                           //gambiarra feita pra contar
                           int contador=1;
                           for(int i=0; i < corpo.length();i++){
                               if(corpo.charAt(i) == '<'){
                                   contador++;
                               }
                           }
                           //separacao dos enderecos em si
                           String[] clientes = corpo.split("<>");//separa em codigo e ip
                           for(int i=0; i<contador; i++){
                               if(!TelaPrivado.isVisible()){

                                listaClientes.add(clientes[i]);
                                TelaPrivado.setParceito(listaClientes.get(i));
                                chatView.setUserLista(listaClientes.get(i));
                               }
                           }

                           //TelaPrivado.setParceito(listaClientes.get(0));
                           //TelaPrivado.setParceito(listaClientes.get(1));

                       }
                       else if(codigo.equals(chavePrivado)){
                           scrollGrupo = false;
                           String[] parts3 = corpo.split(identificador);
                           String parceiro = parts3[0];
                           String corpoMensagemPrivado = parts3[1];
                           for(GUI chat : chatsPrivados){
                               if(chat.getUsername().equals(parceiro)){
                                   chat.getChatWindow().append(parceiro+": "+corpoMensagemPrivado+"\n");
                                   //faz o scrolldown do textArea
                                   chat.getChatWindow().setCaretPosition(chat.getChatWindow().getDocument().getLength()-1);
                               }
                           }
                           chatView.getChatWindow().append("Usuario "+parceiro+" Diz no Privado: "+corpoMensagemPrivado+"\n");                           
                       }
                       else if(codigo.equals(chaveGrupo)){
                            chatView.getChatWindow().append(corpo+"\n");
                            System.out.println("Chegou no cliente: "+corpo);
                            //if(TelaPrivado.isShowing()) chatPrivado.getChatWindow().append(corpo+"\n");
                            if(scrollGrupo){
                                //faz o scrolldown do textArea
                                chatView.getChatWindow().setCaretPosition(chatView.getChatWindow().getDocument().getLength()-1);
                            }
                            scrollGrupo = true;
                       }
                    Thread.sleep(500);

                   } 
                } catch(IOException e){
                    chatView.mostrarErro("Ocorreu um erro ao tentar ler mensagens do socket.");
                    //if(TelaPrivado.isShowing()) chatPrivado.mostrarErro("Ocorreu um erro ao tentar ler mensagens do socket.");


                } catch (InterruptedException ex) {
                    Logger.getLogger(ClienteTCP_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                
            }
        }
    }
   
    class EnterListener implements KeyListener{
        GUI chat;

        public EnterListener(GUI chat){
            this.chat = chat;
        }
        @Override
        public void keyTyped(java.awt.event.KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                this.chat.getEnviarBt().doClick();
            }        
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        }
}


