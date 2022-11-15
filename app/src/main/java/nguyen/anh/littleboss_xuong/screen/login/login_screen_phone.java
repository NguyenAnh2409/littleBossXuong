package nguyen.anh.littleboss_xuong.screen.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.databinding.ActivityLoginScreenPhoneBinding;
import nguyen.anh.littleboss_xuong.screen.MainActivity;
import nguyen.anh.littleboss_xuong.screen.register.register_screen;

public class login_screen_phone extends AppCompatActivity {
    ActivityLoginScreenPhoneBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //login_phone -> login_Username
        binding.tvLoginUserNameLoginPhone.setOnClickListener(v ->callLoginUsername());
        //login_phone -> register
        binding.tvRegisterLoginPhone.setOnClickListener(v ->callRegister());
        //login_phone -> login (continue)
        binding.btnLoginLoginPhone.setOnClickListener(v ->callLoginHome());
    }

    private void callLoginHome() {
        binding.layoutLoginPhone.animate().translationY(2000).setDuration(1000).setStartDelay(300);
        binding.btnLoginLoginPhone.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutLoginPhone.setVisibility(View.GONE);
                startActivity(new Intent(login_screen_phone.this, MainActivity.class));
                finish();
            }
        },500);
    }

    private void callRegister() {
        binding.layoutLoginPhone.animate().translationY(2000).setDuration(1000).setStartDelay(300);
        binding.tvRegisterLoginPhone.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutLoginPhone.setVisibility(View.GONE);
                startActivity(new Intent(login_screen_phone.this, register_screen.class));
                finish();
            }
        },500);
    }

    private void callLoginUsername() {
        binding.layoutLoginPhone.animate().translationY(2000).setDuration(1000).setStartDelay(300);
        binding.tvLoginUserNameLoginPhone.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutLoginPhone.setVisibility(View.GONE);
                startActivity(new Intent(login_screen_phone.this, login_screen.class));
                finish();
            }
        },500);
    }
}