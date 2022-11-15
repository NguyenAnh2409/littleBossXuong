package nguyen.anh.littleboss_xuong.screen.forgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.databinding.ActivityForgotPasswordBinding;
import nguyen.anh.littleboss_xuong.screen.login.login_screen;

public class forgot_Password extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //forgot_password -> forgot_password2 (continue)
        binding.btnContinueForgotPass.setOnClickListener(v ->callForgotPassword2());
    }

    private void callForgotPassword2() {
        binding.layoutForgotpass.animate().translationY(2000).setDuration(1000).setStartDelay(300);
        binding.btnContinueForgotPass.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutForgotpass.setVisibility(View.GONE);
                startActivity(new Intent(forgot_Password.this, forgot_Password2.class));
                finish();
            }
        },500);
    }


}