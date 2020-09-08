package com.angle.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;

import com.angle.animation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private int shortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initially hide the content view.
        binding.content.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        crossfade();
    }

    private void crossfade() {

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
//        将容器设置为透明
        binding.content.setAlpha(0f);
//        可见
        binding.content.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
//       给容器设置动画
        binding.content.animate()
                .alpha(1f)//透明度为1
                .setDuration(shortAnimationDuration)//透明时间
                .setListener(null);//无监听

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        binding.loadingSpinner.animate()
                .alpha(0f)//透明度为0
                .setDuration(shortAnimationDuration)//透明时间
                .setListener(new AnimatorListenerAdapter() {//设置监听
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        ycang
                        binding.loadingSpinner.setVisibility(View.GONE);
                    }
                });
    }

}
