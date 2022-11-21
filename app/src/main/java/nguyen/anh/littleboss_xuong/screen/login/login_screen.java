package nguyen.anh.littleboss_xuong.screen.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import nguyen.anh.littleboss_xuong.databinding.ActivityLoginScreenBinding;
import nguyen.anh.littleboss_xuong.model.User;
import nguyen.anh.littleboss_xuong.network.IRetrofitService;
import nguyen.anh.littleboss_xuong.network.RetrofitBuilder;
import nguyen.anh.littleboss_xuong.screen.MainActivity;
import nguyen.anh.littleboss_xuong.screen.forgotPassword.forgot_Password;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_screen extends AppCompatActivity {
    ActivityLoginScreenBinding binding;
    IRetrofitService iRetrofitService;
    public static User userLogin = new User();
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        binding.edtPassworldLogin.setTransformationMethod(new PasswordTransformationMethod());

        binding.edtUsernameLogin.setText(sharedPreferences.getString("username", ""));
        binding.edtPassworldLogin.setText(sharedPreferences.getString("password", ""));
        binding.checkboxLogin.setChecked(sharedPreferences.getBoolean("remember", false));

        iRetrofitService = new RetrofitBuilder().createService(IRetrofitService.class);
        //login->Phone
        binding.tvLoginPhoneLogin.setOnClickListener(v -> callLoginScreenPhone());
        //login->Home
        binding.btnLoginLogin.setOnClickListener(v -> callLoginScreen());
        //login->Register
        binding.tvRegisterLogin.setOnClickListener(v -> callRegisterScreen());
        //login->Forgot
        binding.tvForgotPassLogin.setOnClickListener(v -> callForgotScreen());
    }

    private void callForgotScreen() {
        //animation
        binding.loginScreenLogin.animate().alpha(1).setDuration(1000);
        binding.loginScreenLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.tvForgotPassLogin.setVisibility(View.GONE);
                startActivity(new Intent(login_screen.this, forgot_Password.class));
            }
        }, 500);
    }

    private void callRegisterScreen() {
        //animation
        binding.loginScreenLogin.animate().alpha(1).setDuration(1000);
        binding.loginScreenLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.tvRegisterLogin.setVisibility(View.GONE);
                startActivity(new Intent(login_screen.this, nguyen.anh.littleboss_xuong.screen.register.register_screen.class));
                finish();
            }
        }, 500);
    }

    private void callLoginScreen() {
        if(binding.edtUsernameLogin.getText().toString().isEmpty() || binding.edtPassworldLogin.getText().toString().isEmpty()){
            binding.textErrorLogin.setText("Vui lòng nhập đầy đủ thông tin");
            return;
        }
        //check email
        if(binding.edtUsernameLogin.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            iRetrofitService.loginEmail(binding.edtUsernameLogin.getText().toString(), binding.edtPassworldLogin.getText().toString()).enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful()) {
                        Map<String, Object> map = response.body();
                        boolean status = Boolean.parseBoolean(String.valueOf(map.get("status")));
                        if (status) {
                            binding.loginScreenLogin.animate().alpha(1).setDuration(1000);
                            binding.loginScreenLogin.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    binding.btnLoginLogin.setVisibility(View.GONE);
                                    Intent intent = new Intent(login_screen.this, MainActivity.class);
                                    String id = String.valueOf(((Map<?, ?>) map.get("result")).get("_id"));
                                    String name = String.valueOf(((Map<?, ?>) map.get("result")).get("name"));
                                    String email = String.valueOf(((Map<?, ?>) map.get("result")).get("email"));
                                    String phone = String.valueOf(((Map<?, ?>) map.get("result")).get("phone"));
                                    userLogin = new User(id, name, email, phone);
                                    if(binding.checkboxLogin.isChecked()){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username", binding.edtUsernameLogin.getText().toString());
                                        editor.putString("password", binding.edtPassworldLogin.getText().toString());
                                        editor.putBoolean("remember", true);
                                        editor.apply();
                                    }else{
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username", "");
                                        editor.putString("password", "");
                                        editor.putBoolean("remember", false);
                                        editor.apply();
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            }, 500);
                        } else {
                            binding.textErrorLogin.setText("Sai tên đăng nhập hoặc mật khẩu");
                        }
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Log.e("Login", "onFailure: " + t.getMessage());
                }
            });
        }else {
            iRetrofitService.loginUsername(binding.edtUsernameLogin.getText().toString(), binding.edtPassworldLogin.getText().toString()).enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    Map<String, Object> map = response.body();
                    boolean status = Boolean.parseBoolean(String.valueOf(map.get("status")));
                    if (status) {
                        //animation
                        binding.loginScreenLogin.animate().alpha(1).setDuration(1000);
                        binding.loginScreenLogin.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.btnLoginLogin.setVisibility(View.GONE);
                                Intent intent = new Intent(login_screen.this, MainActivity.class);
                                String id = String.valueOf(((Map<?, ?>) map.get("result")).get("_id"));
                                String name = String.valueOf(((Map<?, ?>) map.get("result")).get("name"));
                                String email = String.valueOf(((Map<?, ?>) map.get("result")).get("email"));
                                String phone = String.valueOf(((Map<?, ?>) map.get("result")).get("phone"));
                                userLogin = new User(id, name, email, phone);
                                if(binding.checkboxLogin.isChecked()){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", binding.edtUsernameLogin.getText().toString());
                                    editor.putString("password", binding.edtPassworldLogin.getText().toString());
                                    editor.putBoolean("remember", true);
                                    editor.apply();
                                }else {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", "");
                                    editor.putString("password", "");
                                    editor.putBoolean("remember", false);
                                    editor.apply();
                                }
                                startActivity(intent);
                                finish();
                            }
                        }, 500);
                    } else {
                        binding.textErrorLogin.setVisibility(View.VISIBLE);
                        binding.textErrorLogin.setText("Sai tên đăng nhập hoặc mật khẩu");
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    Log.e("Login", "onFailure: " + t.getMessage());
                }
            });
        }
    }

    private void callLoginScreenPhone() {
        //chuyển màn animation
        binding.loginScreenLogin.animate().alpha(1).setDuration(2000);
        binding.loginScreenLogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.loginScreenLogin.setVisibility(View.GONE);
                startActivity(new Intent(login_screen.this, login_screen_phone.class));
                finish();
            }
        }, 500);
    }

}