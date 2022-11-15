package nguyen.anh.littleboss_xuong.screen.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Map;

import nguyen.anh.littleboss_xuong.databinding.ActivityRegisterScreenBinding;
import nguyen.anh.littleboss_xuong.model.User;
import nguyen.anh.littleboss_xuong.network.IRetrofitService;
import nguyen.anh.littleboss_xuong.network.RetrofitBuilder;
import nguyen.anh.littleboss_xuong.screen.login.login_screen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class register_screen extends AppCompatActivity {
    ActivityRegisterScreenBinding binding;
    private IRetrofitService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        service = new RetrofitBuilder().createService(IRetrofitService.class);
//        register -> register(continue)
        binding.btnRegisterRegister.setOnClickListener(v ->callOTP());
        //register -> login
        binding.tvRegisterRegister.setOnClickListener(v ->callLogin());

        binding.edtPassworldConfirmRegister.setTransformationMethod(new PasswordTransformationMethod());
        binding.edtPassworldRegister.setTransformationMethod(new PasswordTransformationMethod());

    }

    private void callOTP() {
        if(binding.edtFullnameRegister.getText().toString() == null ){
            binding.edtFullnameRegister.setError("Không được bỏ trống");
            return;
        }
        if(binding.edtPhoneRegister.getText().toString() == null ){
            binding.edtPhoneRegister.setError("Không được bỏ trống");
            return;
        }
        if(binding.edtEmailRegister.getText().toString() == null ){
            binding.edtEmailRegister.setError("Không được bỏ trống");
            return;
        }
        if(binding.edtPassworldRegister.getText().toString() == null ){
            binding.edtPassworldRegister.setError("Không được bỏ trống");
            return;
        }
        if(binding.edtPassworldConfirmRegister.getText().toString() == null ){
            binding.edtPassworldConfirmRegister.setError("Không được bỏ trống");
            return;
        }
        String fullname = binding.edtFullnameRegister.getText().toString();
        String phone = binding.countryCodePickerRegister.getSelectedCountryCodeWithPlus()+binding.edtPhoneRegister.getText().toString();
        String email = binding.edtEmailRegister.getText().toString();
        String password = binding.edtPassworldRegister.getText().toString();
        String username = binding.edtUsernameRegister.getText().toString();
        if(!password.equals(binding.edtPassworldConfirmRegister.getText().toString())){
            binding.edtPassworldConfirmRegister.setError("Mật khẩu không khớp");
            return;
        }
        if(validateEmail(email) == false){
            binding.edtEmailRegister.setError("Email không hợp lệ");
            return;
        }
        service.Register(
                fullname,
                username,
                email,
                phone,
                password
        ).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if(response.isSuccessful()){
                    Map<String, Object> map = response.body();
                    if(map.get("status").equals(true)){
                        Toast.makeText(register_screen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        callLogin();
                    }
                    else{
                        Toast.makeText(register_screen.this, map.get("message") +"", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });

    }

    private boolean validateEmail(String email){

        if(!email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            return false;
        }
        if(email.length() > 50){
            return false;
        }
        if (email.length() < 6){
            return false;
        }
        return true;
    }

    private void callLogin() {
        binding.layoutRegister.animate().translationY(2000).setDuration(1000).setStartDelay(300);
        binding.tvRegisterRegister.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.layoutRegister.setVisibility(View.GONE);
                startActivity(new Intent(register_screen.this, login_screen.class));
                finish();
            }
        },500);
    }
}