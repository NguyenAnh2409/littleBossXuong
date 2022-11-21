package nguyen.anh.littleboss_xuong.screen.fragment;


import static nguyen.anh.littleboss_xuong.screen.login.login_screen.userLogin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.adapter.AdapterCart;
import nguyen.anh.littleboss_xuong.databinding.FragmentCartBinding;
import nguyen.anh.littleboss_xuong.model.Cart;
import nguyen.anh.littleboss_xuong.model.Product;
import nguyen.anh.littleboss_xuong.network.IRetrofitService;
import nguyen.anh.littleboss_xuong.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Cart extends Fragment {
    FragmentCartBinding binding;
    private AdapterCart adapterCart;
    private IRetrofitService service;
    private List<Cart> listCart = new ArrayList<>();
    double total = 0;
    public static MutableLiveData<Double> totalCart = new MutableLiveData<>();
    public static Fragment_Cart newInstance() {
        Fragment_Cart fragment = new Fragment_Cart();
        return fragment;
    }
    public Fragment_Cart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalCart.observe(this, aDouble -> {
            binding.tvTotalPrice.setText(String.format("%,.0f", aDouble) + " đ");
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnOrder.setOnClickListener(v->onClickOrder());
    }

    public void callAPI(){
        service = RetrofitBuilder.createService(IRetrofitService.class);
        service.getAllCart(userLogin.get_id()).enqueue(new Callback<Map<String, List<Object>>>() {
            @Override
            public void onResponse(Call<Map<String, List<Object>>> call, Response<Map<String, List<Object>>> response) {
                if (response.isSuccessful()) {
                    Map<String, List<Object>> map = response.body();
                    List<Object> list = map.get("data");
                    Log.d("testAPICart", "onResponse: " + list);
                    for (Object o : list) {
                        String product_id = ((Map<String, String>) o).get("product_id");
                        service.getProductDetail(product_id).enqueue(new Callback<Product>() {
                            @Override
                            public void onResponse(Call<Product> call, Response<Product> response) {
                                if (response.isSuccessful()) {
                                    Product product = response.body();
                                    Log.d("testAPICart", "onResponse: " + product);
                                    Cart cart = new Cart();
                                    cart.set_id(((Map<String, String>) o).get("_id"));
                                    cart.setReceipt_id(((Map<String, String>) o).get("receipt_id"));
                                    cart.setName(product.getName());
                                    cart.setPrice(String.valueOf(((Map<String, String>) o).get("price")));
                                    cart.setImage(product.getImage());
                                    double quantity = Double.parseDouble(String.valueOf(((Map<String, Double>) o).get("quantity")));
                                    cart.setQuantity((int) quantity);
                                    cart.setProduct_id(product_id);
                                    listCart.add(cart);
                                }
                                double total = 0;
                                for (int i = 0; i < listCart.size(); i++) {
                                    for (int j = i + 1; j < listCart.size(); j++) {
                                        if (listCart.get(i).getProduct_id().equals(listCart.get(j).getProduct_id())) {
                                            listCart.get(i).setQuantity(listCart.get(i).getQuantity() + listCart.get(j).getQuantity());
                                            listCart.remove(j);
                                            j--;
                                        }
                                    }
                                }
                                for (Cart cart : listCart) {
                                    total += Double.parseDouble(cart.getPrice()) * cart.getQuantity() * 1000;
                                }
                                totalCart.setValue(total);
                                adapterCart = new AdapterCart(listCart, getContext());
                                binding.recyclerviewCart.setAdapter(adapterCart);
                                binding.recyclerviewCart.setLayoutManager(new LinearLayoutManager(getContext()));
                            }

                            @Override
                            public void onFailure(Call<Product> call, Throwable t) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<Map<String, List<Object>>> call, Throwable t) {
                Log.d("testAPICart", "onFailure: " + t.getMessage());
            }
        });

    }

    private void onClickOrder() {
        double total = binding.tvTotalPrice.getText().toString().equals("") ? 0 : Double.parseDouble(binding.tvTotalPrice.getText().toString().replace(",", ""));
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Bạn có muốn đặt hàng?")
                .setContentText("Tổng tiền: " + String.format("%,.0f", total) + " VNĐ")
                .setConfirmText("Đồng ý")
                .setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    service.buyCart(userLogin.get_id(),true,total).enqueue(new Callback<Map<String, Object>>() {
                        @Override
                        public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                            if (response.isSuccessful()) {
                                Map<String, Object> map = response.body();
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Đặt hàng thành công!")
                                        .setContentText("Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(SweetAlertDialog::dismissWithAnimation)
                                        .show();
                                listCart.clear();
                                adapterCart.notifyDataSetChanged();
                                binding.tvTotalPrice.setText("");
                            }
                        }

                        @Override
                        public void onFailure(Call<Map<String, Object>> call, Throwable t) {

                        }
                    });
                })
                .setCancelButton("Hủy", sweetAlertDialog -> sweetAlertDialog.dismissWithAnimation())
                .show();

    }

    public void updateTotal(List<Cart> listCart) {
        double total = 0;
        for (Cart cart : listCart) {
            total += Double.parseDouble(cart.getPrice()) * cart.getQuantity() * 1000;
        }
        totalCart.setValue(total);
    }
    @Override
    public void onResume() {
        super.onResume();
        callAPI();
    }
}