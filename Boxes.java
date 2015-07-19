package com.example.cristian.towerdefence;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Boxes {

    private int num;
    private int x;
    private int y;
    private int ox;
    private int oy;

    public Boxes(){
        Random rand = new Random();
        this.num = 2;
        int randomnum = rand.nextInt(DrawView.available.size());
        this.x = DrawView.available.get(randomnum).getX();
        this.y = DrawView.available.get(randomnum).getY();
        DrawView.available.remove(randomnum);
        //Log.i("WHY", "" + DrawView.available.size());
    }

    public Boxes(int num, int x, int y) {
        this.num = num;
        this.x = x;
        this.y = y;
    }

    public Boxes(int num, int x, int y, int ox, int oy) {
        this.num = num;
        this.x = x;
        this.y = y;
        this.ox = ox;
        this.oy = oy;
    }

    public int getNum(){
        return num;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getOx(){
        return ox;
    }

    public int getOy(){
        return oy;
    }

    public void setNum(int n){
        num = n;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

}
