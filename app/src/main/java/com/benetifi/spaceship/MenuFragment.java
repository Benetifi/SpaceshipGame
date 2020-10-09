package com.benetifi.spaceship;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.benetifi.spaceship.MainActivity.fm;

public class MenuFragment extends Fragment {

    protected MainActivity mainActivity;
    private TextView txtTapToPlay;
    //private ImageView imgSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtTapToPlay = mainActivity.findViewById(R.id.txtTapToPlay);
        mainActivity.findViewById(R.id.layout_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameFragment gameFragment = new GameFragment();
                fm = mainActivity.getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction ();
                ft.addToBackStack(null);
                ft.replace(R.id.main_container, gameFragment).commit();
            }
        });

        /*imgSound = mainActivity.findViewById(R.id.imgSound);
        imgSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound = !sound;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("sound", sound);
                editor.apply();
                btnSoundIcon();
            }
        });
        btnSoundIcon();*/
    }

    /*private void btnSoundIcon(){
        if (sound){
            imgSound.setImageResource(R.drawable.ic_sound_on);
        } else {
            imgSound.setImageResource(R.drawable.ic_sound_off);
        }
    }*/

    private void startBlinkingAnim(){
        Animation startAnim = AnimationUtils.loadAnimation(mainActivity, R.anim.blinking_animation);
        txtTapToPlay.startAnimation(startAnim);
    }

    public void onResume(){
        super.onResume();
        startBlinkingAnim();
    }

    public void onPause(){
        super.onPause();
        txtTapToPlay.setAnimation(null);
    }

    public void onStop(){
        super.onStop();
        txtTapToPlay.setAnimation(null);
    }

    public void onDestroy(){
        super.onDestroy();
        txtTapToPlay.setAnimation(null);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
}
