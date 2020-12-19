package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.word_list , container ,false );

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> words = new ArrayList<Word>();
        words.add( new Word("minto wuksus", "Where are you going?", R.raw.phrase_where_are_you_going ) );
        words.add( new Word("tinnә oyaase'nә", "What is your name?", R.raw.phrase_what_is_your_name ) );
        words.add( new Word("oyaaset...", "My name is...", R.raw.phrase_my_name_is ) );
        words.add( new Word("michәksәs?", "How are you feeling?", R.raw.phrase_how_are_you_feeling) );
        words.add( new Word("kuchi achit", "I’m feeling good.", R.raw.phrase_im_feeling_good ) );
        words.add( new Word("әәnәs'aa?", "Are you coming?", R.raw.phrase_are_you_coming ) );
        words.add( new Word("hәә’ әәnәm", "Yes, I’m coming.", R.raw.phrase_yes_im_coming) );
        words.add( new Word("әәnәm", "I’m coming.", R.raw.phrase_im_coming ) );
        words.add( new Word("yoowutis", "Let’s go.", R.raw.phrase_lets_go ) );
        words.add( new Word("әnni'nem", "Come here.", R.raw.phrase_come_here ) );


        WordAdapter itemAdapter = new WordAdapter(getActivity(), words,R.color.category_phrases );
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

            }
        });
        return  rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer(){
        if(mp != null ){
            mp.release();
            mp = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}