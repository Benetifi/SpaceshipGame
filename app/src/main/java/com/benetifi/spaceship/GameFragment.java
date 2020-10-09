package com.benetifi.spaceship;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowMetrics;

import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {

    protected MainActivity mainActivity;
    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = mainActivity.getWindowManager().getCurrentWindowMetrics();
            Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
            int x = windowMetrics.getBounds().width() - insets.left - insets.right;
            int y = windowMetrics.getBounds().height() - insets.bottom - insets.top;
            gameView = new GameView(mainActivity, x, y);
        } else {
            Point point = new Point();
            mainActivity.getWindowManager().getDefaultDisplay().getSize(point);
            gameView = new GameView(mainActivity, point.x, point.y);
        }
        return gameView;
    }

    @Override
    public void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        gameView.resume();
    }
}
