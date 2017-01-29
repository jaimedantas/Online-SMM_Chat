package com.example.alexandre.chatclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int SERVERPORT;
    private InetAddress SERVERADDR;
    EditText mensagem;
    TextView chatWindow;
    private String msgEnviar;
    private Handler myHandler = new Handler();
    private String username;
    public static String chaveGrupo, chavePrivado, chaveLista, identificador, separador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chaveGrupo = "876527493826";
        chavePrivado = "412432423421";
        chaveLista = "398776778392";
        identificador = "860742707452";
        separador = "742509754578";
        setContentView(R.layout.activity_main);
        mensagem = (EditText) findViewById(R.id.mensagem);
        chatWindow = (TextView) findViewById(R.id.chatWindow);
        chatWindow.setMovementMethod(new ScrollingMovementMethod());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //------------RECEBER DADOS de LoginActivity-------------------------
        Intent i = getIntent();
        try {
            SERVERADDR = InetAddress.getByName(i.getStringExtra("host"));
        } catch(UnknownHostException e){
            System.out.println("Erro ao tentar pegar o host.");
        }
        SERVERPORT = Integer.parseInt(i.getStringExtra("porta"));
        username = i.getStringExtra("username");
        //--------------------------------------------------------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new Thread(new SocketConnect()).start();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_grupo) {
            // Handle the camera action
        } else if (id == R.id.nav_chat_jaime) {

        } else if (id == R.id.nav_chat_ramon) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void criarChat(){
        System.out.println("criar chat");
    }

    public void enviarMensagem(View v) throws IOException {
        msgEnviar = mensagem.getText().toString();
        mensagem.setText("");
        //chatWindow.append(text_to_display);
        //outBuf = text_to_display.getBytes();
        new Thread(new enviarMsg()).start();
    }

    class SocketConnect implements Runnable{
        @Override
        public void run(){
            try {
                System.out.println(SERVERADDR+" "+SERVERPORT);
//                SERVERADDR = InetAddress.getByName("10.50.144.212");
                socket = new Socket(SERVERADDR, SERVERPORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                out.println(username);
                System.out.println("Está conectado? " + socket.isConnected());
                new Thread(new receberMsg()).start();
            } catch (IOException e) {
                System.out.println("Erro ao tentar se conectar.");
            }
        }
    }

    class SocketDisconnect implements Runnable{
        @Override
        public void run(){
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Erro ao tentar fechar socket.");
            }
        }
    }

    class enviarMsg implements Runnable{
        String[] partes;
        String destino, corpoMensagem;
        @Override
        public void run(){
            partes = msgEnviar.split("//");
            if(partes.length == 1){//mensagem publica
                msgEnviar = chaveGrupo+separador+msgEnviar;
            } else if(partes.length == 2){//mensagem privada
                destino = partes[0];
                //caso cliente escreva destino //mensagem, remove espaço adicional
                if(destino.charAt(destino.length() - 1) == ' '){
                    destino = destino.substring(0, destino.length()-1);
                }
                corpoMensagem = partes[1];
                msgEnviar = chavePrivado+separador+destino+identificador+corpoMensagem;//codificacao para msg privada
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        chatWindow.append("(Privada para " + destino + "): " + corpoMensagem+"\n");
                    }
                });
            }
            out.println(msgEnviar);
        }
    }

    class receberMsg implements Runnable{
        String mensagem, origem, corpoMensagem, chave;
        private String[] msgRecebida, mensagemPrivada;

        @Override
        public void run() {

            while(true){
                try{
                    if(in.ready()) {
                        mensagem = in.readLine();
                        if(!mensagem.isEmpty()){
                            msgRecebida = mensagem.split(separador);
                            chave = msgRecebida[0];
                            corpoMensagem = msgRecebida[1];
                            if(chave.equals(chaveGrupo)) {//mensagem pro grupo

                            } else if(chave.equals(chavePrivado)){//mensagem privada
                                mensagemPrivada = corpoMensagem.split(identificador);
                                origem = mensagemPrivada[0];
                                corpoMensagem = "(Mensagem Privada) "+origem+" diz: "+mensagemPrivada[1];
                            }
                            else{//se for a lista de clientes
                                corpoMensagem = "";
                            }
                            System.out.println(corpoMensagem);
                            myHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    chatWindow.append(corpoMensagem + "\n");
                                }
                            });
                        }
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.out.println("Erro recebendo msg");
                }
            }
        }
    }
}
