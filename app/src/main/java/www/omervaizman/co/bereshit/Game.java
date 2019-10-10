package www.omervaizman.co.bereshit;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private Planet[] planets = new Planet[4];
    private long missileStartTime;
    private long smokeStartTime;
    private Player player;
    private MainThread thread;
    private GameActivity gameActivity;
    public SharedPreferences prefs;
    private Background bg;
    private ArrayList<Smokepuff> smoke;
    private ArrayList<Meteor> meteors;
    private boolean newGameCreated;
    private Random rand = new Random();
    private Explosion explosion;
    private int best ;
    private long startReset ;
     private boolean reset;
     private boolean dissapear;
     private boolean started;


    public Game(Context context, GameActivity ga) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        gameActivity =ga;
        setFocusable(true);
       planets[0] = new Planet("earth" , 120,119);
       planets[1]=new Planet( "mars" , 120,115);
       planets[2] = new Planet("jupiter" , 120,120);
       planets[3] = new Planet("moon" , 120,120);
       prefs = gameActivity.getSharedPreferences("records" , 0);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.space2));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.bereshit), 144, 74, 17);
        smoke = new ArrayList<Smokepuff>();
        meteors = new ArrayList<Meteor>();
        missileStartTime = System.nanoTime();
        smokeStartTime = System.nanoTime();
        thread = new MainThread(getHolder(), this);


        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying() && newGameCreated&&reset) {
                player.setPlaying(true);
            }
            if (player.getPlaying()){

                if (!started) started = true;
                reset = false;
                player.setUp(true);

            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        if (player.getPlaying()) {
            bg.update();
            player.update();
            long missileElapsed = (System.nanoTime() - missileStartTime) / 1000000;
            if (missileElapsed > (2000 - player.getScore() / 4)) {
                if (meteors.size() == 0) {
                        meteors.add(new Meteor(BitmapFactory.decodeResource(getResources(), R.drawable.meteor), WIDTH + 10, HEIGHT / 2, 68, 58, player.getScore(), 5));

                } else {
                    if (player.getScore()%10 == 0)
                    {
                        Random rnd = new Random();
                        int i = rnd.nextInt(4);
                        String planet = planets[i].getName();
                        int id = this.getResources().getIdentifier(planet, "drawable", gameActivity.getPackageName());
                        meteors.add(new Meteor(BitmapFactory.decodeResource(getResources(),id)
                                , WIDTH + 10, ((int) ((rand.nextDouble() * ((HEIGHT))))+50), (planets[i].getWidth()-1), (planets[i].getHeight()-1), player.getScore(), 1));

                    }else {

                        meteors.add(new Meteor(BitmapFactory.decodeResource(getResources(), R.drawable.meteor), WIDTH + 10, (int) ((rand.nextDouble() * ((HEIGHT)))), 68, 58, player.getScore(), 5));

                    }}

            missileStartTime = System.nanoTime();

        }
        if ((player.getY() > (getHeight() + 150)) || (player.getY() < 0)) {
            player.setPlaying(false);
        }

        for (int i = 0; i < meteors.size(); i++) {
            meteors.get(i).update();
            if (collision(meteors.get(i), player)) {

                meteors.remove(i);
                player.setPlaying(false);
                break;
            }
            if (meteors.get(i).getX() < -100) {
                meteors.remove(i);
                break;
            }
        }


        long elapsed = (System.nanoTime() - smokeStartTime) / 1000000;
        if (elapsed > 120) {
            smoke.add(new Smokepuff(player.getX(), player.getY() + 10));
            smokeStartTime = System.nanoTime();
        }

        for (int i = 0; i < smoke.size(); i++) {
            smoke.get(i).update();
            if (smoke.get(i).getX() < -10) {
                smoke.remove(i);
            }
        }
    } else

    {
        player.resetDY();
        if (!reset)
        {
            newGameCreated = false;
            startReset = System.nanoTime();
            reset = true;
            dissapear = true ;
            explosion =  new Explosion(BitmapFactory.decodeResource(getResources(), R.drawable.explosion)
                    , player.getX(), player.getY() - 30, 100,100,25);

        }
        explosion.update();
        long resetElapsed = (System.nanoTime() - startReset)/1000000;

        if (resetElapsed> 2500 && !newGameCreated)
        {
            newGame();

        }

    }
    }
    public boolean collision(GameObject a , GameObject b)
    {
        if (Rect.intersects(a.getRect(),b.getRect()))
            return true;
        return false;
    }


        @SuppressLint("MissingSuperCall")
        @Override
        public void draw (Canvas canvas){
            final float scaleFactorX = getWidth() / (WIDTH * 1.f);
            final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
            if (canvas != null) {
                final int savedState = canvas.save();
                canvas.scale(scaleFactorX, scaleFactorY);
                bg.draw(canvas);
                if (!dissapear) {
                    player.draw(canvas);
                }
                for (Smokepuff sp : smoke) {
                    sp.draw(canvas);
                }
                for (Meteor m : meteors) {
                    m.draw(canvas);
                }

                if (started)explosion.draw(canvas);
                drawText(canvas);
                canvas.restoreToCount(savedState);
            }
        }


        public void newGame () {
        dissapear = false;
              player.animation.setFrame(0);
            meteors.clear();
            smoke.clear();
            player.resetDY();
            if ((player.getScore()*3)>getRecord())
            {
                setRecord (player.getScore()*3);
            }
            player.resetScore();
            player.setY(HEIGHT / 2);




            newGameCreated = true;
        }

        public void drawText(Canvas canvas)
        {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("SCORE: "+ (player.getScore()*3),10 , HEIGHT -10 , paint);
            canvas.drawText("BEST: " + getRecord() , WIDTH -215 , HEIGHT -10 , paint );

            if (!player.getPlaying()&&newGameCreated&&reset)
            {
                Paint paint1 = new Paint();
                paint1.setColor(Color.WHITE);
                paint1.setTextSize(40);
                paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas.drawText("PRESS TO PLAY AGAIN" , WIDTH/2-50 , HEIGHT/2 , paint1);

                paint1.setTextSize(20);
                canvas.drawText("Press and hold to go up", WIDTH/2-50 , HEIGHT/2+20 , paint1 );
                canvas.drawText("Realease to go down", WIDTH/2-50 , HEIGHT/2+40 , paint1 );

            }

        }

        public int getRecord()
        {
           prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
            return  prefs.getInt("record" , 0);
        }

        public void setRecord(int value)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("record", value);
            editor.commit();
        }

}
