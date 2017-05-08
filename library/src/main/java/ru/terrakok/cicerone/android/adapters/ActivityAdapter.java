package ru.terrakok.cicerone.android.adapters;


import android.content.Context;
import android.content.Intent;

public interface ActivityAdapter {
    void startActivity(Intent activityIntent);

    void finish();

    Context getContext();
}
