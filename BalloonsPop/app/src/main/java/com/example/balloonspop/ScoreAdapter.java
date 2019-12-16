/*
 * Written by Khoa L. D. Ho (klh170130)
 * for Assignment 6 for class CS6326 Falll 2019, by Professor J. Cole, at UT Dallas,
 * starting Nov16, 2019, using Android Studio 191 on Windows 8.1
 *
 * Balloons Pop Game Program
 * This Program is a simple balloons pop game
 *
 *
 *
 * This is a custom Adapter to display the HighScore items in columns on a listView layout, using a prepared custom layout for each item.
 *
 */


package com.example.balloonspop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ScoreAdapter extends BaseAdapter {
    Context context;
    ArrayList<HighScore> scoreArrayList;

    // constructor
    public ScoreAdapter(Context aContext, ArrayList<HighScore> aScoreArrayList)
    {
        this.context = aContext;
        this.scoreArrayList = aScoreArrayList;
    }

    @Override
    public int getCount()
    {
        return scoreArrayList.size();
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public Object getItem(int position) {
        return scoreArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {

        protected TextView txvName, txvScore, txvDate;
    }

    @Override
    public View getView(int idx, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_highscore_listview_item, null, true);

            holder.txvName = (TextView) convertView.findViewById(R.id.txvName);
            holder.txvScore = (TextView) convertView.findViewById(R.id.txvScore);
            holder.txvDate  = (TextView) convertView.findViewById(R.id.txvDate);



            convertView.setTag(holder);
        }
        else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        SimpleDateFormat dtFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        //String xy = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        //holder.txvName.setText(xy);
        holder.txvName.setText(scoreArrayList.get(idx).getScoreName());
        holder.txvScore.setText(String.valueOf(scoreArrayList.get(idx).getScore() ) );
        holder.txvDate.setText( dtFormat.format(scoreArrayList.get(idx).getScoreDate() ) ) ;

        int[] colors = new int[] { 0xFF999999, 0xFF000000 };
        int idxPos = idx % 2;
        holder.txvName.setBackgroundColor(colors[idxPos]);
        holder.txvScore.setBackgroundColor(colors[idxPos]);
        holder.txvDate.setBackgroundColor(colors[idxPos]);
        convertView.setBackgroundColor(colors[idxPos]);

        return convertView;
    }

}
