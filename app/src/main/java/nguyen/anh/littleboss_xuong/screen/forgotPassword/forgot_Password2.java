package nguyen.anh.littleboss_xuong.screen.forgotPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.databinding.ActivityForgotPassword2Binding;
import nguyen.anh.littleboss_xuong.databinding.ActivityForgotPasswordBinding;

public class forgot_Password2 extends AppCompatActivity {

    ActivityForgotPassword2Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPassword2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //forgot_password2 -> confirm (continue)
        binding.btnConfirmForgotPass.setOnClickListener(v ->callConfirm());

    }

    private void callConfirm() {
        //cập nhật thành công
        Toast.makeText(this, "cập nhật thành công", Toast.LENGTH_SHORT).show();
    }
}