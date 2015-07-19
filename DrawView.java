package com.example.cristian.towerdefence;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawView extends View {
    Paint paint, paint2, threedigits, scoretext;
    public static int w;
    public static int h;
    private ArrayList<Boxes> boxes = new ArrayList<Boxes>();
    public static ArrayList<Boxes> available = new ArrayList<Boxes>();
    public float fingerx, fingery;
    public int bestscore = 0;
    private Context thiscontext;
    public static DrawView drawview;
    public static ArrayList<Boxes> tomove = new ArrayList<Boxes>();

    public DrawView(Context context) {
        super(context);
        drawview = this;
        thiscontext = context;
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(100);
        paint2 = new Paint();
        paint2.setTextAlign(Paint.Align.LEFT);
        paint2.setTextSize(80);
        scoretext = new Paint();
        scoretext.setTextAlign(Paint.Align.LEFT);
        scoretext.setTextSize(60);
        threedigits = new Paint();
        threedigits.setTextAlign(Paint.Align.CENTER);
        threedigits.setTextSize(80);
        SoundPlayer.initSounds(context);
        available = new ArrayList<Boxes>();
        boxes = new ArrayList<Boxes>();
        for(int i = 0; i<4; i++) {
            for (int y = 0; y < 4; y++) {
                available.add(new Boxes(2, y, i));
            }
        }
        boxes.add(new Boxes());
        AnimateBoxes anim = new AnimateBoxes(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void onDraw(Canvas canvas) {
        AnimateBoxes.canvas = canvas;
        paint.setColor(Color.GRAY);
        //paint.setColor(Color.rgb(181, 181, 181));
        paint.setColor(Color.rgb(186, 174, 162));

        int padding = 40;
        int boxarea = w-padding*2;
        int fromheight = h-boxarea-padding;
        //canvas.drawRect(padding, fromheight, boxarea+padding, h-padding, paint);
        canvas.drawRoundRect(new RectF(padding, fromheight, boxarea + padding, h-padding), 6, 6, paint);
        int numpad = 20;
        int numsize = (boxarea-numpad*5)/4;

        //paint.setColor(Color.LTGRAY);
        //paint.setColor(Color.rgb(205, 205, 205));
        paint.setColor(Color.rgb(205, 193, 181));
        for(int i = 0; i<4; i++) {
            for (int y = 0; y < 4; y++) {
                //canvas.drawRect(padding+(numpad + numsize)*y+numpad, fromheight + numpad + (numpad + numsize)*i, padding+(numpad + numsize)*y+numpad+numsize, fromheight + numpad + (numpad + numsize)*i+numsize, paint);
                canvas.drawRoundRect(new RectF(padding+(numpad + numsize)*y+numpad, fromheight + numpad + (numpad + numsize)*i, padding+(numpad + numsize)*y+numpad+numsize, fromheight + numpad + (numpad + numsize)*i+numsize), 6, 6, paint);
            }
        }

        int score = 0;
        for(int i = 0; i<boxes.size(); i++) {

            //paint.setColor(Color.rgb(245, 245, 245));
            int boxnum = boxes.get(i).getNum();
            int boxx = boxes.get(i).getX();
            int boxy = boxes.get(i).getY();
            score+=(((int)(Math.log(boxnum)/Math.log(2)+1e-10)))*(int)Math.pow(2,(((int)(Math.log(boxnum)/Math.log(2)+1e-10))));
            switch(boxnum){
                case 2:
                    paint.setColor(Color.rgb(245, 245, 245));
                    break;
                case 4:
                    paint.setColor(Color.rgb(236,224,202));
                    break;
                case 8:
                    paint.setColor(Color.rgb(242,177,121));
                    break;
                case 16:
                    paint.setColor(Color.rgb(245,149,101));
                    break;
                case 32:
                    paint.setColor(Color.rgb(245,124,95));
                    break;
                case 64:
                    paint.setColor(Color.rgb(246,93,59));
                    break;
                case 128:
                    paint.setColor(Color.rgb(237,206,113));
                    break;
                case 256:
                    paint.setColor(Color.rgb(237,204,99));
                    break;
                case 512:
                    paint.setColor(Color.rgb(237,200,80));
                    break;
                default:
                    paint.setColor(Color.rgb(245, 245, 245));
                    break;
            }

            //Log.i("Boxes", "x: "+boxx+", y: "+boxy);
            canvas.drawRoundRect(new RectF(padding + (numpad + numsize) * boxx + numpad, fromheight + numpad + (numpad + numsize) * boxy, padding + (numpad + numsize) * boxx + numpad + numsize, fromheight + numpad + (numpad + numsize) * boxy + numsize), 6, 6, paint);
            if(boxnum>7)
                paint.setColor(Color.WHITE);
            else
                paint.setColor(Color.rgb(122,110,98));
            if(boxnum>100) {
                threedigits.setColor(Color.WHITE);
                canvas.drawText(("" + boxnum), padding + (numpad + numsize) * boxx + numpad + numsize / 2, fromheight + numpad + (numpad + numsize) * boxy + numsize / 2 + 35, threedigits);
            } else {
                canvas.drawText(("" + boxnum), padding + (numpad + numsize) * boxx + numpad + numsize / 2, fromheight + numpad + (numpad + numsize) * boxy + numsize / 2 + 35, paint);
            }
            //canvas.drawText((""+boxnum), 0, 0, paint);
        }
        if(score>bestscore)
            bestscore = score;
        canvas.drawText("Cristian's 2048", padding, 150, paint2);
        canvas.drawText(("Score: "+score), padding, 250, scoretext);
        canvas.drawText(("Best Score: " + bestscore), padding, 340, scoretext);

        if(available.size()==0) {
            paint.setColor(Color.argb(200, 255, 255, 255));
            canvas.drawRoundRect(new RectF(padding, fromheight, boxarea + padding, h - padding), 6, 6, paint);
            paint.setColor(Color.rgb(122, 110, 98));
            canvas.drawText(("Game Over :("), boxarea / 2 + padding, fromheight + boxarea / 2, paint);
        }

        AnimateBoxes.drawThings(canvas, boxes);

        //canvas.drawText(("Click to try again?"), boxarea/2 + padding, fromheight+boxarea/2+50, paint);
    }

    public void moveBoxes(int d){
        ArrayList<Boxes> whichmove = new ArrayList<Boxes>();
        switch(d) {
            case 0:
                for(int i = 0; i<boxes.size(); i++) {
                    int ydist = boxes.get(i).getY();
                    int abletomove = ydist;
                    for(int y = 0; y<boxes.size(); y++) {
                        if (boxes.get(i).getX() == boxes.get(y).getX()&&
                                boxes.get(i).getY()>boxes.get(y).getY()) {
                            //Log.i("Boxes", "Case 0: "+boxes.get(i).getX()+", "+boxes.get(i).getY()+": "+boxes.get(y).getX()+", "+boxes.get(y).getY());
                                abletomove--;
                        }
                    }
                    //if(ydist-abletomove<0||ydist-abletomove>3)
                     //   Log.wtf("WHY IS IT DOING THIS", "Case 0");
                    //boxes.get(i).setY(ydist-abletomove);
                    whichmove.add(new Boxes(boxes.get(i).getNum(), boxes.get(i).getX(), ydist-abletomove));
                }
                break;
            case 1:
                for(int i = 0; i<boxes.size(); i++) {
                    int xdist = 3-boxes.get(i).getX();
                    int abletomove = xdist;
                    boolean combined = false;
                    for(int y = 0; y<boxes.size(); y++) {
                        if (boxes.get(i).getX() < boxes.get(y).getX()&&
                                boxes.get(i).getY() == boxes.get(y).getY()) {
                            //Log.i("Boxes", "Case 1: "+boxes.get(i).getX()+", "+boxes.get(i).getY()+": "+boxes.get(y).getX()+", "+boxes.get(y).getY());
                            abletomove--;
                        }
                    }
                    //if((3-(xdist-abletomove))<0||(3-(xdist-abletomove))>3)
                    //    Log.wtf("WHY IS IT DOING THIS", "Case 1");
                    //boxes.get(i).setX(3 - (xdist - abletomove));
                    whichmove.add(new Boxes(boxes.get(i).getNum(), 3-(xdist-abletomove), boxes.get(i).getY()));
                }
                break;
            case 2:
                for(int i = 0; i<boxes.size(); i++) {
                    int ydist = 3-boxes.get(i).getY();
                    int abletomove = ydist;
                    for(int y = 0; y<boxes.size(); y++) {
                        if (boxes.get(i).getX() == boxes.get(y).getX()&&
                                boxes.get(i).getY()<boxes.get(y).getY()) {
                            abletomove--;
                            //Log.i("Boxes", "Case 2: "+boxes.get(i).getX()+", "+boxes.get(i).getY()+": "+boxes.get(y).getX()+", "+boxes.get(y).getY());
                        }
                    }
                    //if((3-(ydist-abletomove))<0||(3-(ydist-abletomove))>3)
                    //    Log.wtf("WHY IS IT DOING THIS", "Case 2");
                    //boxes.get(i).setY(3-(ydist-abletomove));
                    whichmove.add(new Boxes(boxes.get(i).getNum(), boxes.get(i).getX(), 3-(ydist-abletomove)));
                }
                break;
            case 3:
                for(int i = 0; i<boxes.size(); i++) {
                    int xdist = boxes.get(i).getX();
                    int abletomove = xdist;
                    for(int y = 0; y<boxes.size(); y++) {
                        if (boxes.get(i).getX() > boxes.get(y).getX()&&
                                boxes.get(i).getY() == boxes.get(y).getY()) {
                            //Log.i("Boxes", "Case 3: "+boxes.get(i).getX()+", "+boxes.get(i).getY()+": "+boxes.get(y).getX()+", "+boxes.get(y).getY());
                            abletomove--;
                        }
                    }
                    //if(xdist-abletomove<0||xdist-abletomove>3)
                    //    Log.wtf("WHY IS IT DOING THIS", "Case 3");
                    //boxes.get(i).setX(xdist-abletomove);
                    whichmove.add(new Boxes(boxes.get(i).getNum(), xdist-abletomove, boxes.get(i).getY()));
                }
                break;
        }

        for(int i = 0; i<whichmove.size(); i++){
            boxes.get(i).setX(whichmove.get(i).getX());
            boxes.get(i).setY(whichmove.get(i).getY());
        }

        ArrayList<Integer> potential = new ArrayList<Integer>();
        whichmove = new ArrayList<Boxes>();
        switch(d) {
            case 0:
                for(int i = 0; i<boxes.size(); i++) {
                    int ydist = boxes.get(i).getY();
                    for(int y = 0; y<boxes.size(); y++) {
                        boolean found = false;
                        for(int p: potential){
                            if(y==p||i==p) {
                                found = true;
                                break;
                            }
                        }
                        if(found)
                            continue;
                        if (boxes.get(i).getX() == boxes.get(y).getX()&&
                                boxes.get(i).getY()>boxes.get(y).getY()) {
                            if(boxes.get(i).getNum()==boxes.get(y).getNum()&&
                                    boxes.get(i).getY()-boxes.get(y).getY()==1){
                                if(!(boxes.get(i).getY()==2&&boxes.get(y).getY()==0)) {
                                    boxes.get(y).setNum(boxes.get(y).getNum() * 2);
                                    for(int h = boxes.get(i).getY()+1; h<=3; h++) {
                                        whichmove.add(new Boxes(d, boxes.get(i).getX(), h));
                                    }
                                    if(y<i)
                                        potential.add(new Integer(y));
                                    else
                                        potential.add(new Integer(y-1));
                                    tomove.add(new Boxes(0, boxes.get(y).getX(), boxes.get(y).getY(), boxes.get(i).getX(), boxes.get(i).getY()));
                                    boxes.remove(i);
                                    i--;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 1:
                for(int i = 0; i<boxes.size(); i++) {
                    int xdist = 3-boxes.get(i).getX();
                    for(int y = 0; y<boxes.size(); y++) {
                        boolean found = false;
                        for(int p: potential){
                            if(y==p||i==p) {
                                found = true;
                                break;
                            }
                        }
                        if(found)
                            continue;
                        if (boxes.get(i).getX() < boxes.get(y).getX()&&
                                boxes.get(i).getY() == boxes.get(y).getY()) {
                            if(boxes.get(i).getNum()==boxes.get(y).getNum()&&
                                    boxes.get(y).getX()-boxes.get(i).getX()==1){
                                if(!(boxes.get(i).getX()==1&&boxes.get(y).getX()==3)){
                                    boxes.get(y).setNum(boxes.get(y).getNum() * 2);
                                    for(int h = 0; h<boxes.get(i).getX(); h++) {
                                        whichmove.add(new Boxes(d, h, boxes.get(i).getY()));
                                    }
                                    if(y<i)
                                        potential.add(new Integer(y));
                                    else
                                        potential.add(new Integer(y-1));
                                    tomove.add(new Boxes(0, boxes.get(y).getX(), boxes.get(y).getY(), boxes.get(i).getX(), boxes.get(i).getY()));
                                    boxes.remove(i);
                                    i--;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 2:
                for(int i = 0; i<boxes.size(); i++) {
                    int ydist = 3-boxes.get(i).getY();
                    for(int y = 0; y<boxes.size(); y++) {
                        boolean found = false;
                        for(int p: potential){
                            if(y==p||i==p) {
                                found = true;
                                break;
                            }
                        }
                        if(found)
                            continue;
                        if (boxes.get(i).getX() == boxes.get(y).getX()&&
                                boxes.get(i).getY()<boxes.get(y).getY()) {
                            if(boxes.get(i).getNum()==boxes.get(y).getNum()&&
                                    boxes.get(y).getY()-boxes.get(i).getY()==1){
                                if(!(boxes.get(i).getY()==1&&boxes.get(y).getY()==3)) {
                                    boxes.get(y).setNum(boxes.get(y).getNum() * 2);
                                    for(int h = 0; h < boxes.get(i).getY(); h++) {
                                        whichmove.add(new Boxes(d, boxes.get(i).getX(), h));
                                    }
                                    if(y<i)
                                        potential.add(new Integer(y));
                                    else
                                        potential.add(new Integer(y-1));
                                    tomove.add(new Boxes(0, boxes.get(y).getX(), boxes.get(y).getY(), boxes.get(i).getX(), boxes.get(i).getY()));
                                    boxes.remove(i);
                                    i--;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 3:
                for(int i = 0; i<boxes.size(); i++) {
                    int xdist = boxes.get(i).getX();
                    for(int y = 0; y<boxes.size(); y++) {
                        boolean found = false;
                        for(int p: potential){
                            if(y==p||i==p) {
                                found = true;
                                break;
                            }
                        }
                        if(found)
                            continue;
                        if (boxes.get(i).getX() > boxes.get(y).getX()&&
                                boxes.get(i).getY() == boxes.get(y).getY()) {
                            if(boxes.get(i).getNum()==boxes.get(y).getNum()&&
                                    boxes.get(i).getX()-boxes.get(y).getX()==1) {
                                if (!(boxes.get(i).getX() == 2 && boxes.get(y).getX() == 0)) {
                                    boxes.get(y).setNum(boxes.get(y).getNum() * 2);
                                    for(int h = boxes.get(i).getX()+1; h<=3; h++) {
                                        whichmove.add(new Boxes(d, h, boxes.get(i).getY()));
                                    }
                                    if(y<i)
                                        potential.add(new Integer(y));
                                    else
                                        potential.add(new Integer(y-1));
                                    tomove.add(new Boxes(0, boxes.get(y).getX(), boxes.get(y).getY(), boxes.get(i).getX(), boxes.get(i).getY()));
                                    boxes.remove(i);
                                    i--;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
        }

        moveCertainBoxes(whichmove);

        //Log.i("yes", "here?");
        available = new ArrayList<Boxes>();
        for(int i = 0; i<4; i++) {
            for (int y = 0; y < 4; y++) {
                boolean boxthere = false;
                for (int n = 0; n < boxes.size(); n++) {
                    if(boxes.get(n).getX()==y&&boxes.get(n).getY()==i){
                        boxthere = true;
                        break;
                    }
                }
                if(!boxthere)
                    available.add(new Boxes(2, y, i));
            }
        }

        if(available.size()>0)
            boxes.add(new Boxes());
        this.invalidate();
        SoundPlayer.playSound(thiscontext);

    }



    public void moveCertainBoxes(ArrayList<Boxes> move) {
        ArrayList<Boxes> whichmove = new ArrayList<Boxes>();
        for(int i = 0; i<move.size(); i++) {
            int d = move.get(i).getNum();
            int ydist = move.get(i).getY();
            int xdist = move.get(i).getX();
            int abletomove;
            Log.i("WHYYYY", "x: "+xdist+", y: "+ydist);
            switch (d) {
                case 0:
                    ydist = move.get(i).getY();
                    abletomove = ydist;
                    for (int y = 0; y < boxes.size(); y++) {
                        if (move.get(i).getX() == boxes.get(y).getX() &&
                                move.get(i).getY() > boxes.get(y).getY()) {
                            abletomove--;
                        }
                    }
                    whichmove.add(new Boxes(move.get(i).getNum(), move.get(i).getX(), ydist - abletomove, move.get(i).getX(), move.get(i).getY()));
                    break;
                case 1:
                    xdist = 3 - move.get(i).getX();
                    abletomove = xdist;
                    for (int y = 0; y < boxes.size(); y++) {
                        if (move.get(i).getX() < boxes.get(y).getX() &&
                                move.get(i).getY() == boxes.get(y).getY()) {
                            abletomove--;
                        }
                    }
                    whichmove.add(new Boxes(move.get(i).getNum(), 3 - (xdist - abletomove), move.get(i).getY(), move.get(i).getX(), move.get(i).getY()));
                    break;
                case 2:
                    ydist = 3 - move.get(i).getY();
                    abletomove = ydist;
                    for (int y = 0; y < boxes.size(); y++) {
                        if (move.get(i).getX() == boxes.get(y).getX() &&
                                move.get(i).getY() < boxes.get(y).getY()) {
                            abletomove--;
                        }
                    }
                    whichmove.add(new Boxes(move.get(i).getNum(), move.get(i).getX(), 3 - (ydist - abletomove), move.get(i).getX(), move.get(i).getY()));
                    break;
                case 3:
                    abletomove = xdist;
                    for (int y = 0; y < boxes.size(); y++) {
                        if (move.get(i).getX() > boxes.get(y).getX() &&
                                move.get(i).getY() == boxes.get(y).getY()) {
                            abletomove--;
                        }
                    }
                    whichmove.add(new Boxes(move.get(i).getNum(), xdist - abletomove, move.get(i).getY(), move.get(i).getX(), move.get(i).getY()));
                    break;
            }
        }

        for (int i = 0; i < whichmove.size(); i++) {
            for (int y = 0; y < boxes.size(); y++) {
                if(boxes.get(y).getX()==whichmove.get(i).getOx()&&
                        boxes.get(y).getY()==whichmove.get(i).getOy()) {
                    boxes.get(y).setX(whichmove.get(i).getX());
                    boxes.get(y).setY(whichmove.get(i).getY());
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x2, y2, dx, dy;
        int direction;
        if(available.size()==0){
            available = new ArrayList<Boxes>();
            boxes = new ArrayList<Boxes>();
            for(int i = 0; i<4; i++) {
                for (int y = 0; y < 4; y++) {
                    available.add(new Boxes(2, y, i));
                }
            }
            boxes.add(new Boxes());
            return true;
        }

        switch(event.getAction()) {
            case(MotionEvent.ACTION_DOWN):
                fingerx = event.getX();
                fingery = event.getY();
                break;
            case(MotionEvent.ACTION_UP):
                x2 = event.getX();
                y2 = event.getY();
                dx = x2-fingerx;
                dy = y2-fingery;

                // Use dx and dy to determine the direction
                if(Math.abs(dx) > Math.abs(dy)) {
                    if(dx>0) direction = 1;
                    else direction = 3;
                } else {
                    if(dy>0) direction = 2;
                    else direction = 0;
                }
                //Log.i("WHY", "WHY "+direction);
                moveBoxes(direction);
                break;

        }
        return true;
    }

}