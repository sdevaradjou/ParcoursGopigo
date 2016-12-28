package com.paris8.pro.parcoursgopigo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;

public class MainActivity extends Activity {

    MySurfaceView mySurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }

    class MySurfaceView extends SurfaceView implements Runnable {

        Path path;

        Thread thread = null;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Random r, aa, bb;

        float a;
        float b;

        int x = 0;

        public MySurfaceView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(15);
            paint.setColor(Color.WHITE);
            r = new Random();
            aa = new Random();
            bb = new Random();
            a = 0;
            b = 0;
        }

        /*@Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                path = new Path();
                path.moveTo(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                path.lineTo(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                path.lineTo(event.getX(), event.getY());
            }

            if (path != null) {
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawPath(path, paint);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            return true;
        }*/

        public void onResumeMySurfaceView() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseMySurfaceView() {
            boolean retry = true;
            running = false;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        private void bougerRobot(int commande) {
            //RECUPERER LA DIRECTION AVEC LA METHODE TRADUIREDIRECTION
            //ET L'ENVOYER DANS CELLE CI EN PARAMETRE POUR FAIRE BOUGER LE ROBOT
            //IL PEUT AUSSI S'ARRETER (A ESSAYER)
            int var = commande;
            switch (var) {
                case 1: //nord
                    b -= 50;
                    path.rLineTo(a, b);
                    break;
                case 2: //est
                    a+=50;
                    path.rLineTo(a, b);
                    break;
                case 3: //sud
                    b+=50;
                    path.rLineTo(a, b);
                    break;
                case 4: //ouest
                    a-=50;
                    path.rLineTo(a, b);
                    break;
                case 0: //stop
                    path.close();
                    break;
                default:
                    break;
            }
        }

        /*private int traduireDirection(String direction){
            //RECUPERER LA DIRECTION A PARTIR DE LA BDD AVEC UN GETDIRECTION
            //LE METTRE EN PARAMETRE DE CETTE METHODE
            //ENVOYER LE RETOUR DE CETTE METHODE VERS LA METHODE BOUGERROBOT
            int commande;
            switch (direction){
                case "N":
                    commande = 1;
                    return commande;
                    break;
                case "E":
                    commande = 2;
                    return commande;
                    break;
                case "S":
                    commande = 3;
                    return commande;
                    break;
                case "O":
                    commande = 4;
                    return commande;
                    break;
                case "":
                    commande = 0;
                    return commande;
                    break;
                default:
                    break;
            }
            return 0;
        }*/

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (running) {
                if (surfaceHolder.getSurface().isValid()) {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    //... actual drawing on canvas

                    path = new Path();
                    path.moveTo(1280, 720);

//                    bougerRobot(1);
//                    bougerRobot(4);
//                    bougerRobot(3);
                    bougerRobot(2);
//                    bougerRobot(0);

                    canvas.drawPath(path, paint);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


/*
import android.app.Activity;

import android.content.Context;

import android.graphics.Canvas;

import android.graphics.Color;

import android.graphics.Paint;

import android.graphics.Path;

import android.os.Bundle;

import android.view.View;



public class MainActivity extends Activity {



    */
/**
 * Called when the activity is first created.
 *//*


    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(new MyView(this));

    }



    public class MyView extends View {



        class Pt{

            float x, y;



            Pt(float _x, float _y){

                x = _x;

                y = _y;

            }

        }



        Pt[] myPath = { new Pt(100, 100),

                new Pt(200, 200),

                new Pt(200, 500),

                new Pt(400, 500),

                new Pt(400, 200)

        };



        public MyView(Context context) {

            super(context);

            // TODO Auto-generated constructor stub

        }



        @Override

        protected void onDraw(Canvas canvas) {

            // TODO Auto-generated method stub

            super.onDraw(canvas);





            Paint paint = new Paint();

            paint.setColor(Color.WHITE);

            paint.setStrokeWidth(3);

            paint.setStyle(Paint.Style.STROKE);

            Path path = new Path();



            path.moveTo(myPath[0].x, myPath[0].y);

            for (int i = 1; i < myPath.length; i++){

                path.lineTo(myPath[i].x, myPath[i].y);

            }

            canvas.drawPath(path, paint);



        }



    }

}*/
