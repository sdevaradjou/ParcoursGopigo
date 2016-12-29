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
            //APPELER CETTE METHODE DANS LA METHODE RUN TOUT EN BAS
            int var = commande;
            switch (var) {
                case 1: //nord est      NE
                    a += 50;
                    b -= 50;
                    path.rLineTo(a, b);
                    bougerRobot(commande);
                    break;
                case 2: //nord ouest    NO
                    a -= 50;
                    b -= 50;
                    path.rLineTo(a, b);
                    bougerRobot(commande);
                    break;
                case 3: //sud est       SE
                    a += 50;
                    b += 50;
                    path.rLineTo(a, b);
                    bougerRobot(commande);
                    break;
                case 4: //sud ouest     SO
                    a -= 50;
                    b += 50;
                    path.rLineTo(a, b);
                    bougerRobot(commande);
                    break;
                case 0: //stop
                    path.close();
                    bougerRobot(commande);
                    break;
                default:
                    break;
            }
        }

        private int traduireDirection(String direction1, String direction2){
            //RECUPERER LA DIRECTION A PARTIR DE LA BDD AVEC UN GETDIRECTION
            //LE METTRE EN PARAMETRE DE CETTE METHODE
            //ENVOYER LE RETOUR DE CETTE METHODE VERS LA METHODE BOUGERROBOT
            //APPELER CETTE METHODE DANS LA METHODE RUN TOUT EN BAS
            int commande;

            if (direction1=="N" && direction2=="E") {
                commande = 1;
                return commande;
            }
            else if(direction1=="N" && direction2=="O"){
                commande = 2;
                return commande;
            }
            else if(direction1=="S" && direction2=="E"){
                commande = 3;
                return commande;
            }
            else if(direction1=="S" && direction2=="O"){
                commande = 4;
                return commande;
            }
            else if(direction1=="" && direction2==""){
                commande = 0;
                return commande;
            }
            else if(direction1=="" || direction2==""){
                commande = 0;
                return commande;
            }

            return 0;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (running) {
                if (surfaceHolder.getSurface().isValid()) {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    //... actual drawing on canvas

                    path = new Path();
                    path.moveTo(1280, 720);

//-----------------------------------A PLACER LE SOURIRE DE REVAULT ICI-----------------------------
//                               LA PETITE BANANE DES FAMILLES QUI VA BIEN
                    //OU SINON LES METHODES CORRESPONDANTES EVENTUELLEMENT AUSSI SA SERAIT BIEN =p


//                    bougerRobot(1);
//                    bougerRobot(4);
//                    bougerRobot(3);
//                    bougerRobot(2);
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
