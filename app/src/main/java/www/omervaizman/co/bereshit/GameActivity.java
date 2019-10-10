package www.omervaizman.co.bereshit;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity  {
    Game game ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game(this, this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(game);
        Intent intent = getIntent();
       // Intent intent2  = new Intent(this, MainActivity.class);
        //intent2.putExtra("record", game.prefs.getInt("record", 0 ));



    }
    @Override
    public void onBackPressed()
    {
        Intent intent  = new Intent(this, MainActivity.class);
        intent.putExtra("record", game.prefs.getInt("record", 0 ));
        startActivity(intent);
    }
}