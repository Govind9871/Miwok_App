package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    public WordAdapter(Activity contest, ArrayList<Word> wordList , int colorResourceId ){
        super(contest, 0, wordList );
        mColorResourceId = colorResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listWordView = convertView;
        if( listWordView == null ){
            listWordView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentView = getItem(position);

        LinearLayout textComtainer = listWordView.findViewById(R.id.text_Container);
        int color = ContextCompat.getColor( getContext(), mColorResourceId );
        textComtainer.setBackgroundColor( color );

        TextView miwokTextView = (TextView) listWordView.findViewById(R.id.miwok_text_view);
        // Get the version name from the current AndroidList object and
        // set this text on the name TextView
        miwokTextView.setText(currentView.getMiwokTranslation() );

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listWordView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentView.getDefaultTranslation());

        ImageView image = listWordView.findViewById((R.id.image));

        if( !currentView.hasImage() ){
            image.setVisibility(View.GONE);
        }else {
            image.setImageResource(currentView.getImageResourceId() );
        }
        return listWordView;
    }
}
