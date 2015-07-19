package com.example.cristian.towerdefence;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class AnimateBoxes {

    private Handler handler = new Handler();
    public static int x = 0;
    public static Paint paint;
    public static Canvas canvas;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            animateBoxes();
            handler.postDelayed(this, 5);
        }
    };

    public AnimateBoxes(Context context) {
        paint = new Paint();
        handler.postDelayed(runnable, 5);
    }

    public static void drawThings(Canvas canvas, ArrayList<Boxes> boxes) {
        paint.setColor(Color.GRAY);
        //canvas.drawRect(x, 10, x + 20, 30, paint);
        //x++;
        ArrayList<Boxes> tomove = DrawView.tomove;

        Log.i("WHY", "x: " + tomove.size());
        int movelength = 10;
        int padding = 40;
        int boxarea = DrawView.w-padding*2;
        int fromheight = DrawView.h-boxarea-padding;
        int numpad = 20;
        int numsize = (boxarea-numpad*5)/4;

        for(int i = 0; i<tomove.size(); i++){
            if(tomove.get(i).getNum()==movelength) {
                tomove.remove(i);
                i--;
                continue;
            }
            for(int y = 0; y<boxes.size(); y++){
                if(tomove.get(i).getX()==boxes.get(y).getX()&&
                        tomove.get(i).getY()==boxes.get(y).getY()){
                    int boxnum = boxes.get(y).getNum();
                    double boxx = tomove.get(i).getOx()+ (double)tomove.get(i).getNum()*(double)(tomove.get(i).getX()-tomove.get(i).getOx())/movelength;
                    double boxy = tomove.get(i).getOy()+ (double)tomove.get(i).getNum()*(double)(tomove.get(i).getY()-tomove.get(i).getOy())/movelength;
                    Log.i("WHY", "Boxx: "+boxx);
                    Log.i("WHY", "Boxy: "+boxy);
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
                    canvas.drawRoundRect(new RectF(padding +(int)((numpad + numsize) * boxx) + numpad, fromheight + numpad + (int)((numpad + numsize) * boxy), padding + (int)((numpad + numsize) * boxx) + numpad + numsize, fromheight + numpad + (int)((numpad + numsize) * boxy) + numsize), 6, 6, paint);

                    tomove.get(i).setNum(tomove.get(i).getNum()+1);
                }
            }
        }
    }

    private void animateBoxes(){
        DrawView.drawview.invalidate();
    }
}
