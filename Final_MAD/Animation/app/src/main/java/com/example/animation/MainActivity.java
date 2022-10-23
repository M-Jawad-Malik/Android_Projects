package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.animation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
//rate of change in animation: interpolator
ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
    public  void  zoomInImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.zoomin);
        binding.imgvw.startAnimation(animation);
    }
    public  void  zoomOutImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.zoomout);
        binding.imgvw.startAnimation(animation);
    }
    public  void  fadeOutImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.fadeout);
        binding.imgvw.startAnimation(animation);
    }
    public  void  fadeInImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.fadein);
        binding.imgvw.startAnimation(animation);
    }
    public  void  slideDownImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.slidedown);
        binding.imgvw.startAnimation(animation);
    }
    public  void  slideUpImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.slideup);
        binding.imgvw.startAnimation(animation);
    }

    public  void  rotateImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.rotate_anim);
        binding.imgvw.startAnimation(animation);
    }
    public  void  blinkImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.blink);
        binding.imgvw.startAnimation(animation);
    }
    public  void  moveImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.move);
        binding.imgvw.startAnimation(animation);
    }

    public  void  bounceImage(View view){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        binding.imgvw.startAnimation(animation);
    }

}