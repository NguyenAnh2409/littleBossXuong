package nguyen.anh.littleboss_xuong.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nguyen.anh.littleboss_xuong.databinding.ActivitySplashScreenBinding;
import nguyen.anh.littleboss_xuong.screen.login.login_screen;

public class Splash_Screen extends AppCompatActivity {

    ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //full screen
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


        binding.splashScreen.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.splashScreen.animate().alpha(0).setDuration(2000);
                binding.splashScreen.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.splashScreen.setVisibility(View.GONE);
                        startActivity(new Intent(Splash_Screen.this, login_screen.class));
                        finish();
                    }
                },1000);
            }
        },1000);
    }
}