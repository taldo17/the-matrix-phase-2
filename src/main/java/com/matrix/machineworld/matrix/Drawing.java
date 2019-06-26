package com.matrix.machineworld.matrix;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Drawing extends JPanel {

    private int FONT_SIZE = 32, SCREEN_SIZE = 700;
    DrawingThread[] thArr = new DrawingThread[SCREEN_SIZE / FONT_SIZE];
    public Drawing(){
        for (int i = 0; i < thArr.length; i++) {
            thArr[i] = new DrawingThread(i* FONT_SIZE);
        }
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        g.fillRect(0, 0, SCREEN_SIZE, SCREEN_SIZE);
        g.setColor(Color.BLACK);
        Font font = new Font("Monospaced", Font.BOLD, FONT_SIZE);
        g2.setFont(font);
        for (int i = 0; i < thArr.length; i++) {
            if(thArr[i].y > 700){
                thArr[i] = new DrawingThread(i* FONT_SIZE);
            }
            drawThread(g2,thArr[i]);
        }

        try{Thread.sleep(30);}catch(Exception ex){}

        repaint();
    }
    public void drawThread(Graphics2D g2, DrawingThread th){
        int fontsize = g2.getFont().getSize();
        for (int i = 0; i < th.len; i++) {
            if(th.randInt(0, th.len) == i)
                th.chArr[i][0] = th.randChar();
            if(i == th.len-1)
                g2.setColor(Color.WHITE);
            else
                g2.setColor(Color.GREEN);
            g2.drawChars(th.chArr[i] ,0 ,1 ,th.x , th.y + (i*fontsize));
        }
        th.y+=th.vel;
    }

    public class DrawingThread {
        int vel;
        int len;
        int x;
        int y;
        char[][] chArr;

        DrawingThread(int x){

            this.x = x;
            len = randInt(5,30);
            chArr = new char[len][1];
            chArr = populateArrWithChars(chArr);
            vel = randInt(1,5);
            this.y = (-1)*len* FONT_SIZE;
        }
        public char[][] populateArrWithChars(char[][] arr){
            for (int i = 0; i < arr.length; i++) {
                arr[i][0] = randChar();
            }
            return arr;
        }
        public char randChar(){
            final String alphabet = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
            final int N = alphabet.length();
            Random r = new Random();
            return alphabet.charAt(r.nextInt(N));
        }
        public int randInt(int min, int max) {
            Random rand = new Random();
            return rand.nextInt((max - min) + 1) + min;
        }
    }
}