package com.angle.cardfilp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.angle.animation.R;

public class CardFlipActivity extends FragmentActivity {
    private boolean showingBack;
    private View viewYellow;
    private View viewBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_card_flip);

        viewBlue = findViewById(R.id.imageView);
        viewYellow = findViewById(R.id.imageView2);

//       如果没有保存
        if (savedInstanceState == null) {
//       添加默认容器
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new CardFrontFragment())
                    .commit();
        }
//      监听Contaner
        FrameLayout container = findViewById(R.id.container);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });

        viewYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewYellow.setVisibility(View.INVISIBLE);
                showPerHideCard();
            }
        });
        viewBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBlue.setVisibility(View.INVISIBLE);
                hideShowCardBack();
            }
        });
    }

    private void showPerHideCard() {
        // previously invisible view
        View myView = findViewById(R.id.imageView);

        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = myView.getWidth() / 2;
            int cy = myView.getHeight() / 2;

            // get the final radius for the clipping circle
            float finalRadius = (float) Math.hypot(cx, cy);

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius);

            // make the view visible and start the animation
            myView.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            // set the view to invisible without a circular reveal animation below Lollipop
            myView.setVisibility(View.INVISIBLE);
        }


    }

    private void hideShowCardBack() {
        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = viewYellow.getWidth() / 2;
            int cy = viewYellow.getHeight() / 2;

            // get the initial radius for the clipping circle
            float initialRadius = (float) Math.hypot(cx, cy);

            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(viewYellow, cx, cy, initialRadius, 0f);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    viewYellow.setVisibility(View.INVISIBLE);
                }
            });

            // start the animation
            anim.start();
        } else {
            // set the view to visible without a circular reveal animation below Lollipop
            viewYellow.setVisibility(View.VISIBLE);
        }


    }

    private void flipCard() {
        if (showingBack) {
            getSupportFragmentManager().popBackStack();
            return;
        }
        // Flip to the back.

        showingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for
        // the back of the card, uses custom animations, and is part of the fragment
        // manager's back stack.
        getSupportFragmentManager()
                .beginTransaction()
                // Replace the default fragment animations with animator resources
                // representing rotations when switching to the back of the card, as
                // well as animator resources representing rotations when flipping
                // back to the front (e.g. when the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                // Replace any fragments currently in the container view with a
                // fragment representing the next page (indicated by the
                // just-incremented currentPage variable).
                .replace(R.id.container, new CardBackFragment())
                // Add this transaction to the back stack, allowing users to press
                // Back to get to the front of the card.
                .addToBackStack(null)
                // Commit the transaction.
                .commit();
    }


}

