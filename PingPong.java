package ping.pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;
import javax.swing.JFrame;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingPong extends JFrame implements MouseMotionListener, KeyListener {

    public PingPong() {
        setSize(1365,768);
        setVisible(true);
        setBackground(Color.black);
        addMouseMotionListener(this);
        addKeyListener(this);
        player_y = 384;
        rival_y = player_y;
        bola_y = player_y;
        bola_x = 684;
        barraAltura = 130;
        barraComp = 50;
        bolaComp = 15;
        direcao = -1;
        inicio = true;
        temp = player_y;
    }
    
    Color fundo_cor = Color.black;
    Color player_cor = Color.white;
    Color rival_cor = Color.orange;
    Color bola_cor = Color.green;
    int player_y, rival_y, bola_y, bola_x, barraAltura, barraComp, bolaComp, temp;
    int player_score = 0, rival_score = 0;
    byte direcao;
    boolean inicio;
    float fator_y = 0;
    
    public void iniciarQuadro(Graphics g) {
        int c = barraAltura;
        g.setColor(player_cor);
        g.fillRect(0,player_y-(c/2),barraComp,c);
        g.setColor(rival_cor);
        g.fillRect(1365-barraComp,rival_y-(c/2),barraComp,c);
        g.setColor(Color.white);
        g.fillRect(679,0,6,768);
        g.setColor(bola_cor);
        g.fillOval(bola_x-(bolaComp/2),bola_y-(bolaComp/2),bolaComp,bolaComp);
    }
    
    public void moverBola(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(679,0,6,768);
        int c = bolaComp;
        g.setColor(fundo_cor);
        g.fillOval(bola_x-(c/2),bola_y-(c/2),c,c);
        if ((bola_x == barraComp && player_y-(barraAltura/2) <= bola_y && bola_y <= player_y+(barraAltura/2)) || (bola_x+c == 1365-barraComp && rival_y-(barraAltura/2) <= bola_y && bola_y <= rival_y+(barraAltura/2))) {
            direcao *= -1;
            if (bola_x == barraComp)
                fator_y -= (player_y-bola_y)/35;
             else
               fator_y -= (rival_y-bola_y)/35;
        }
        if (bola_y == 28 || bola_y == 768)
            fator_y *= -1;
        bola_x += direcao;
        bola_y += fator_y;
        g.setColor(bola_cor);
        g.fillOval(bola_x-(c/2),bola_y-(c/2),c,c);
        if (bola_x == 0 || bola_x == 1365-c) {
            g.setColor(fundo_cor);
            g.fillRect(400,400,50,20);
            g.setColor(Color.green);
            if (bola_x == 0)
                rival_score++;
             else
               player_score++;
            g.drawString(player_score+" - "+rival_score,400,395);
            fator_y *= -1;
        }
    }
    
    public void moverJogador(Graphics g) {
        int c = barraAltura;
        g.setColor(fundo_cor);
        g.fillRect(0,player_y-(c/2),barraComp,c);
        player_y = temp;
        g.setColor(player_cor);
        g.fillRect(0,player_y-(c/2),barraComp,c);
    }
    
    public void moverRival(Graphics g) {
        int c = barraAltura;
        g.setColor(fundo_cor);
        g.fillRect(1365-barraComp,rival_y-(c/2),barraComp,c);
        if (bola_y > rival_y && rival_y < 718)
            rival_y++;
         else
           if (bola_y < rival_y && rival_y > 28+c/2)
               rival_y--;
        g.setColor(rival_cor);
        g.fillRect(1365-barraComp,rival_y-(c/2),barraComp,c);
    }
    
    public void paint(Graphics g) {
        Timer t = new Timer();
        if (inicio) {
            iniciarQuadro(g);
            inicio = false;
            /*TimerTask tt = new TimerTask() {
                @Override 
                public void run() {
                    repaint();
                }
            };t.scheduleAtFixedRate(tt,10,10);*/
        }
        moverBola(g);
        moverJogador(g);
        moverRival(g);
    }

    @Override
    public void mouseMoved(MouseEvent m) {
    }
    
    @Override
    public void mouseDragged(MouseEvent m) {
    }
    
    @Override
    public void keyTyped(KeyEvent k) {
    }

    @Override
    public void keyPressed(KeyEvent k) {
        int c = barraAltura;
        if ((k.getKeyCode() == 38 || k.getKeyCode() == 87) && player_y > 28+c/2) {
            temp = player_y-10;
        }
         else
           if ((k.getKeyCode() == 40 || k.getKeyCode() == 83) && player_y < 718)
               temp = player_y+10;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent k) {
    }
    
    public static void main(String[] args) {
        PingPong pp = new PingPong();
    }    


}
