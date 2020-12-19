package com.example.android.miwok;


public class Word {
    private String miwokTranslation;
    private String defaultTranslation;
    private static final int N0_IMAGE_PROVIDER = - 1;
    private int imageResourceId = N0_IMAGE_PROVIDER;
    private int mAudioResourceId;

    public Word(String miwok, String english , int audioResourceId ) {
        miwokTranslation = miwok;
        defaultTranslation = english;
        mAudioResourceId = audioResourceId;
    }

    public Word(String miwok, String english ,int ImageURI , int audioResourceId) {
        miwokTranslation = miwok;
        defaultTranslation = english;
        imageResourceId = ImageURI;
        mAudioResourceId = audioResourceId;
    }

    public int getImageResourceId() { return imageResourceId; }

    @Override
    public String toString() {
        return "Word{" +
                "miwokTranslation='" + miwokTranslation + '\'' +
                ", defaultTranslation='" + defaultTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public boolean hasImage(){
        return imageResourceId != N0_IMAGE_PROVIDER;
    }

    public int getmAudioResourceId() { return mAudioResourceId; }
}
