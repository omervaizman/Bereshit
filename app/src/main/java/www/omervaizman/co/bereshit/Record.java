package www.omervaizman.co.bereshit;

import android.graphics.Bitmap;

public class Record {
    private int score;
    private int place ;


    public Record(int place , int score )
    {

        this.score = score ;
        this.place = place  ;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
