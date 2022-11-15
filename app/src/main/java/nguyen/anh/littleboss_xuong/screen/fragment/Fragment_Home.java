package nguyen.anh.littleboss_xuong.screen.fragment;

import static nguyen.anh.littleboss_xuong.screen.login.login_screen.userLogin;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.adapter.AdapterCategory;
import nguyen.anh.littleboss_xuong.adapter.AdapterProduct;
import nguyen.anh.littleboss_xuong.databinding.FragmentHomeBinding;
import nguyen.anh.littleboss_xuong.model.Category;
import nguyen.anh.littleboss_xuong.model.Product;
import nguyen.anh.littleboss_xuong.network.IRetrofitService;
import nguyen.anh.littleboss_xuong.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {
    FragmentHomeBinding binding;
    public static final Fragment_Home instans = new Fragment_Home();
    private IRetrofitService iRetrofitService;
    private AdapterCategory adapterCategory;
    private AdapterProduct adapterProduct;
    private List<Category> categories = new ArrayList<>();
    private List<Product> products,products2 = new ArrayList<>();
    public MutableLiveData<List<Product>> listProduct = new MutableLiveData<>();
    public Fragment_Home() {
        // Required empty public constructor

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iRetrofitService = new RetrofitBuilder().createService(IRetrofitService.class);

        binding.tvTitle.setText("Chào "+userLogin.getName()+"!");
        binding.tcProduct.setOnClickListener(v->onClickProduct());
        binding.pkProduct.setOnClickListener(v->onClickProduct2());
        iRetrofitService.getAllCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();
                    categories.get(0).setCh(true);
                    adapterCategory = new AdapterCategory(categories,Fragment_Home.this);
                    binding.recyclerView.setAdapter(adapterCategory);
                    binding.recyclerView.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    binding.recyclerView.setLayoutManager(linearLayoutManager);
                    updateProduct(categories.get(0).get_id());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

        listProduct.observe(getViewLifecycleOwner(), products -> {
            AdapterProduct adapterProduct = new AdapterProduct(products, Fragment_Home.this);
            binding.recyclerView2.setAdapter(adapterProduct);
            binding.recyclerView2.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            binding.recyclerView2.setLayoutManager(gridLayoutManager);
        });

    }

    private void onClickProduct2() {
        Toast.makeText(getContext(), "Product", Toast.LENGTH_SHORT).show();
        binding.pkProduct.setBackground(getResources().getDrawable(R.drawable.bg_item_caterogy));
        binding.pkProduct.setTextColor(getResources().getColor(R.color.white));
        binding.tcProduct.setBackground(getResources().getDrawable(R.drawable.bg_item_caterogy2));
        binding.tcProduct.setTextColor(getResources().getColor(R.color.black));
        products2 = new ArrayList<>();
        for(int i = 0; i < products.size(); i++){
            if(!products.get(i).isPet()){
                products2.add(products.get(i));
            }
        }
        listProduct.setValue(products2);
    }

    private void onClickProduct() {
        Toast.makeText(getContext(), "Product", Toast.LENGTH_SHORT).show();
        binding.pkProduct.setBackground(getResources().getDrawable(R.drawable.bg_item_caterogy2));
        binding.pkProduct.setTextColor(getResources().getColor(R.color.black));
        binding.tcProduct.setBackground(getResources().getDrawable(R.drawable.bg_item_caterogy));
        binding.tcProduct.setTextColor(getResources().getColor(R.color.white));
        products2 = new ArrayList<>();
        for(int i = 0; i < products.size(); i++){
            if(products.get(i).isPet()){
                products2.add(products.get(i));
            }
        }
        listProduct.setValue(products2);
    }

    public void updateProduct(String id){
        products = new ArrayList<>();
        products2 = new ArrayList<>();
        Log.d("TAG", "updateProduct: " + id);
        iRetrofitService.getAllProductbyId(id).enqueue(new Callback<Map<String, List<Product>>>() {
            @Override
            public void onResponse(Call<Map<String, List<Product>>> call, Response<Map<String, List<Product>>> response) {
                    if (response.isSuccessful()) {
                        Map<String, List<Product>> map = response.body();
                        products = map.get("data");
                        Log.d("TAG", "onResponse: " + products);
                        if(products.size() > 0){
                            for(int i = 0; i < products.size(); i++){
                                if(products.get(i).isPet()){
                                    products2.add(products.get(i));
                                }
                            }
                        }
                        if(products2.size() <= 0) {
                            Toast.makeText(getContext(), "Không có sản phẩm nào", Toast.LENGTH_SHORT).show();
                        }
                        listProduct.setValue(products2);
                    }
            }
            @Override
            public void onFailure(Call<Map<String, List<Product>>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
        onClickProduct();
    }
}