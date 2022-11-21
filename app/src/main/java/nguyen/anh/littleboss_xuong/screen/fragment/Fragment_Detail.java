package nguyen.anh.littleboss_xuong.screen.fragment;

import static nguyen.anh.littleboss_xuong.screen.MainActivity.productMain;
import static nguyen.anh.littleboss_xuong.screen.login.login_screen.userLogin;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.Format;
import java.util.List;
import java.util.Map;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.databinding.FragmentDetailBinding;
import nguyen.anh.littleboss_xuong.model.Cart;
import nguyen.anh.littleboss_xuong.network.IRetrofitService;
import nguyen.anh.littleboss_xuong.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Detail extends Fragment {

    FragmentDetailBinding binding;
    private IRetrofitService service;
    public Fragment_Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        service = RetrofitBuilder.createService(IRetrofitService.class);
        binding.imgDetailBack.setOnClickListener(v->onClickBack());
        binding.btnAddCart.setOnClickListener(v->onClickAddCart());
        binding.imgMinus.setOnClickListener(v->onClickMinus());
        binding.imgPlus.setOnClickListener(v->onClickPlus());
        fillData();
    }

    private void onClickPlus() {
        int number = Integer.parseInt(binding.tvNumber.getText().toString());
        number++;
        int quantity = Integer.parseInt(productMain.getQuantity());
        if(number >= quantity){
            Toast.makeText(getContext(), "Số lượng tối đa là " + quantity, Toast.LENGTH_SHORT).show();
            return;
        }
        binding.tvNumber.setText(number+"");
    }

    private void onClickMinus() {
        int number = Integer.parseInt(binding.tvNumber.getText().toString());
        if(number>1){
            number--;
            binding.tvNumber.setText(number+"");
        }
    }

    private void onClickAddCart() {
        String price = productMain.getPrice().replace(",", "");
        service.addCart(userLogin.get_id(), null,productMain.get_id(), Integer.parseInt(binding.tvNumber.getText().toString()), Double.parseDouble(price)).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("testAPI", "onResponse: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d("testAPI", "onFailure: " + t.getMessage());
            }
        });
    }

    private void onClickBack() {
        Fragment fragment = new Fragment();
        Class fragmentClass = Fragment_Home.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.fragment_container, fragment).commit();
        }
    }

    private void fillData() {
        binding.tvNameDeatil.setText(productMain.getName());
        double price = Double.parseDouble(productMain.getPrice()) * 1000;
        binding.tvPriceDeatil.setText(String.format("%,.0f", price) + " đ");
        binding.tvDescriptionDeatil.setText(productMain.getDescribes());
        binding.tvQuatityDeatil.setText("Số lượng: "+productMain.getQuantity());
        Picasso.get().load(productMain.getImage()).into(binding.imgDetail);
    }
}