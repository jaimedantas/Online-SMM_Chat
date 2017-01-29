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
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexandre
 */
class Clientes extends Thread{
    public BufferedReader in;
    public PrintWriter out;
    public InetAddress address;
    public Socket socket;
    public String username;
    
    public Clientes(Socket s) throws IOException{
        socket = s;
        address = socket.getInetAddress();
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
    }
}
