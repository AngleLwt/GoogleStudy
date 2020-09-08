package com.angle.googlestudy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.angle.googlestudy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toast();
        count();

    }

    private void count() {
        binding.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                binding.tv.setText(count + "");
            }
        });
    }

    private void toast() {
        binding.toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = binding.tv.getText().toString();
                if (number != null) {
                    Toast.makeText(MainActivity.this, number, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
