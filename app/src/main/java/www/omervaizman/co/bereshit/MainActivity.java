package www.omervaizman.co.bereshit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnPlay , btnRecords;
    int record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        record = intent.getIntExtra("record" , 0 );
        btnPlay = (Button)findViewById(R.id.btnPl);

        btnRecords = (Button)findViewById(R.id.btnRecords);
        btnRecords.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnPlay)
        {
            Intent intent = new Intent(this , GameActivity.class);
            startActivity(intent);
        }
        if (v==btnRecords)
        {
            Intent intent = new Intent(this , RecordActivity.class);
            intent.putExtra("record" , record);
            startActivity(intent);
        }
    }
}