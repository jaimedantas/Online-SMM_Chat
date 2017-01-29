/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author alexandre
 */
public class ServerTCP {
    private static ArrayList<Clientes> listaClientes;
    private static ServerSocket serverSocket;
    private static final int PORT = 7777;
    public static String separador;
    public static String identificador;
    public static String chaveGrupo, chavePrivado, chaveLista;
    private static TESTE tela;

    /**
     *
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        listaClientes = new ArrayList<>();
        serverSocket = new ServerSocket(PORT);
        chaveGrupo = "876527493826";
        chavePrivado = "412432423421";
        chaveLista = "398776778392";
        identificador = "860742707452";
        separador = "742509754578";
        tela = new TESTE();
        tela.pack();
        tela.setLocationRelativeTo(null);
        tela.setTitle("Seridor em Execução");
        tela.setVisible(true);
        //Thread que vai aguardar pelas mensagens a serem recebidas
        //por cada cliente
        class aguardarMsg extends Thread{
            public BufferedReader in;
            Clientes cliente;
            String NomesClientes;
            public aguardarMsg(Clientes c){//construtor
                tela.setTexto("Aguardando mensagens de "+c.socket.getInetAddress()+"\n");
                cliente = c;
                in = cliente.in;
            }
            
            @Override
            public void run(){
                char testeConection;
                while(true){
                    try{
                        //checar periodicamente se a conexão foi fechada
                        //caso alguém se desconecte, excluir da lista de clientes,
                        //enviar para todos um aviso de que o cliente se desconectou
                        //e enviar a nova lista de clientes após o cliente ter sido
                        //removido da lista
                        testeConection = (char) cliente.in.read();
                        if(testeConection == (char)-1){
                            tela.setTexto(cliente.socket.getInetAddress()+" acabou de se desconectar.\n");
                            listaClientes.remove(cliente);
                            enviarMsgTodos(chaveGrupo+separador+cliente.username+" acabou de se desconectar.");
                            NomesClientes = GetNomesClientes();  
                            enviarMsgTodos(chaveLista+separador+NomesClientes);
                            tela.setTexto("Lista Clientes " + NomesClientes+"\n");
                            break;
                        }
                        //caso alguma mensagem tenha sido recebida
                        
                        if(in.ready()){
                            String mensagem = in.readLine();
                            mensagem = testeConection+mensagem;
                            tela.setTexto("Mensagem do cliente "+mensagem+"\n");
                            String[] parts = mensagem.split(separador);//separa em codigo e ip
                            String codigo = parts[0];
                            String corpoMensagem;
                            if(parts.length < 2)//se receber uma mensagem vazia
                                corpoMensagem = "";
                             else
                                corpoMensagem = parts[1];
                            tela.setTexto(corpoMensagem+"\n");
                            if(codigo.equals(chavePrivado)){  
                                String[] parts2 = corpoMensagem.split(identificador);
                                try{
                                    String parceiro = parts2[0];
                                    String corpoMensagemPrivado = parts2[1];
                                    tela.setTexto(cliente.username+" Solicitou chat privado com "+parceiro+"\n");
                                    enviarMsgPrivado(cliente.username, parceiro, corpoMensagemPrivado);
                                    NomesClientes = GetNomesClientes();
                                    enviarMsgTodos(chaveLista+separador+NomesClientes);
                                }
                               catch(Exception e){
                                    tela.setTexto("erro na leitura da string\n");
                                }

                            }
                            else{
                                enviarMsgTodos(chaveGrupo+separador+cliente.username+" diz: "+corpoMensagem);
                                //System.out.println(corpoMensagem);
                                //tela.setTexto("Lista Clientes " + NomesClientes+"\n");
                                NomesClientes = GetNomesClientes();
                                enviarMsgTodos(chaveLista+separador+NomesClientes);

                            }
                        }
                    } catch(IOException e){
                        tela.setTexto("Erro ao tentar receber msg\n");
                        //try {
                          //  testeConection = (char) cliente.in.read();
                            //if(testeConection == (char)-1){
                                enviarMsgTodos(chaveGrupo+separador+cliente.username+" acabou de se desconectar.");
                                listaClientes.remove(cliente);
                                NomesClientes = GetNomesClientes();
                                enviarMsgTodos(chaveLista+separador+NomesClientes);
                                break;
                        //}
                        //} catch (IOException ex) {
                          //  Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                        //}
                    }
                    /*try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                }
            }
        }
        
        //Thread que vai aguardar por uma nova conexão
        //1 - espera por uma conexão ser feita ao serverSocket
        //2 - cria um novo cliente e adiciona a lista de clientes
        //3 - inicia o listener de mensagens, para caso alguma mensagem seja recebida
        class EsperarConexao extends Thread{
            PrintWriter aviso;//usado para enviar avisos aos sockets recentes
            BufferedReader in;
            String NomesClientes;
            @Override
            public void run(){
                tela.setTexto("Esperando conexão na porta "+PORT+"\n");
                while(true){
                    try {
                        //tenta aceitar uma nova conexão
                        Socket socket = serverSocket.accept();
                        aviso = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        //se tiver mais do que 15 clientes conectados, rejeitar conexão
                        if(listaClientes.size()>=15){
                            aviso.println("Desculpe, o número máximo de clientes foi atingido");
                            socket.close();
                        }
                        else{
                            //cria um novo cliente
                            Clientes novoCliente = new Clientes(socket);
                            //receber username
                            String username = in.readLine();
                            //setar username do cliente
                            novoCliente.username = username;
                            //cria um novo listener para aquele cliente
                            aguardarMsg listener = new aguardarMsg(novoCliente);
                            //adiciona o novo cliente a lista de clientes
                            listaClientes.add(novoCliente);
                            //envia mensagem ao novo cliente
                            aviso.println(chaveGrupo+separador+"Bem vindo ao servidor.");
                            //imprime o endereço do cliente que conectou
                            //enviarMsgTodos(socket.getInetAddress()+" acabou de se conectar.");
                            //inicia a thread para o novo cliente
                            new Thread(listener).start();
                            //enviar aviso no grupo que alguém se conectou
                            enviarMsgTodos(chaveGrupo+separador+"Servidor: "+novoCliente.username+" entrou no chat.");
                            //toda vez que alguém se conectar, enviar a lista de clientes para todos
                            NomesClientes = GetNomesClientes();
                            enviarMsgTodos(chaveLista+separador+NomesClientes);
                            tela.setTexto("Lista Clientes " + NomesClientes+"\n");

                        }
                        //Thread.sleep(200);
                    } catch (IOException ex) {
                        tela.setTexto("Erro na criação de socket.\n");
                    } 
                    //catch (InterruptedException ex) {
                    //    Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
                    //}
                }
            }
        }
        
        //inicia a thread para esperar por conexões novas
        new Thread(new EsperarConexao()).start();
        
    }
    
    //enviar mensagem para todos os clientes conectados
    public static void enviarMsgTodos(String mensagem){
        for (Clientes cliente : listaClientes) {
            cliente.out.println(mensagem);
        }
    }
    
    public static void enviarMsgPrivado(String origem, String destino, String mensagem){
        for (Clientes cliente : listaClientes) {
            if(cliente.username.equals(destino)){
                cliente.out.println(chavePrivado+separador+origem+identificador+mensagem);
                tela.setTexto(mensagem+"\n");
            }
        }
    }
    public static String GetNomesClientes(){
        String retorno = "";
        if(!listaClientes.isEmpty()){
            retorno = listaClientes.get(0).username;
            for (int i=1; i<listaClientes.size();i++){
                retorno = retorno +"<>"+ listaClientes.get(i).username;
            }
        }
        return retorno;
    }
    
}
