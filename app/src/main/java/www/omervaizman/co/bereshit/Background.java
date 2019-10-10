package www.omervaizman.co.bereshit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background {
    private Bitmap image;
    private int x, y , dx;

    public Background(Bitmap bitmap) {
        this.image = bitmap;

        dx = Game.MOVESPEED;
    }
    public void update(){

        x+= dx ;
        if (x<-Game.WIDTH)
        {
            x = 0;
        }


    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image , x , y , null);
        if (x<0)
        {
            canvas.drawBitmap(image , x+Game.WIDTH , y ,null);
        }

    }

}
