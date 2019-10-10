package www.omervaizman.co.bereshit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Record> recordList = new ArrayList<Record>();
    RecordAdapter recordAdapter;
    int newRecord;
    Record r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        Intent intent = getIntent();
        newRecord = intent.getIntExtra("record", 0 );
        r= new Record(recordList.size()+1 , newRecord);
        recordList.add(r);
        recordAdapter = new RecordAdapter(this, r.getScore(),r.getPlace() , recordList);
        lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(recordAdapter);


    }
}
