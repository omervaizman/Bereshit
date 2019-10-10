package www.omervaizman.co.bereshit;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecordAdapter extends ArrayAdapter<Record> {
    Context context;
    List<Record> recordList;

    public RecordAdapter(Context context, int textViewResourceId1, int textViewResourceId2, List<Record> objects) {
        super(context, textViewResourceId1, textViewResourceId2, objects);

        this.context=context;
        this.recordList=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.record_row,parent,false);
        TextView tvPlace = (TextView) view.findViewById(R.id.tvPlace);
        TextView tvScore = (TextView) view.findViewById(R.id.tvScore);
        Record temp =  recordList.get(position);
        tvPlace.setText(String.valueOf(temp.getPlace())+".");
        tvScore.setText(String.valueOf(temp.getScore()));
        return view;
    }


}
