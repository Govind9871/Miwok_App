package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


public class NumbersFragment extends Fragment {

    private MediaPlayer mp;
    private AudioManager mAudioManager;

    private final AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume. We'll treat
                        // both cases the same way because our app is playing short sound files.

                        // Pause playback and reset player to the start of the file. That way, we can
                        // play the word from the beginning when we resume playback.
                        mp.pause();
                        mp.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        mp.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    }
                }
            };

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public NumbersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list , container ,false );

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add( new Word("lutti", "one" , R.drawable.number_one , R.raw.number_one ) );
        words.add( new Word("otiiko", "two",R.drawable.number_two , R.raw.number_two ) );
        words.add( new Word("tolookosu", "three", R.drawable.number_three , R.raw.number_three ) );
        words.add( new Word("oyyisa", "four", R.drawable.number_four , R.raw.number_four ) );
        words.add( new Word("massokka", "five", R.drawable.number_five , R.raw.number_five ) );
        words.add( new Word("temmokka", "six", R.drawable.number_six ,R.raw.number_six ) );
        words.add( new Word("kenekaku", "seven", R.drawable.number_seven ,R.raw.number_seven ) );
        words.add( new Word("kawinta", "eight", R.drawable.number_eight , R.raw.number_eight ) );
        words.add( new Word("wo'e", "nine", R.drawable.number_nine , R.raw.number_nine ) );
        words.add( new Word("na'aacha", "ten", R.drawable.number_ten , R.raw.number_ten ) );

        /*
         here WordAdapter is a custom class which extends ArrayAdapter class to override getView method to display our own set of
         view on our layout list_item which shows them in a list
        */

        WordAdapter itemAdapter = new WordAdapter(getActivity(), words , R.color.category_numbers );
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                Word word = words.get( position );
                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    mp = MediaPlayer.create(getActivity(), word.getmAudioResourceId() );
                    mp.start();
                    mp.setOnCompletionListener(mp1 -> releaseMediaPlayer() );
                }
                Log.v("NumberActivity","Current word" + word );
            }

        });

        return rootView;
    }

    private void releaseMediaPlayer(){
        if(mp != null ){
            mp.release();
            mp = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}