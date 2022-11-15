package nguyen.anh.littleboss_xuong.screen.fragment;

import static nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Detail.cartMutableLiveData;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.adapter.AdapterCart;
import nguyen.anh.littleboss_xuong.databinding.FragmentCartBinding;


public class Fragment_Cart extends Fragment {
    FragmentCartBinding binding;
    private AdapterCart adapterCart;

    public Fragment_Cart() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        cartMutableLiveData.observe(getViewLifecycleOwner(),carts -> {
            adapterCart = new AdapterCart(carts,getContext());
            binding.recyclerviewCart.setAdapter(adapterCart);
            binding.recyclerviewCart.setHasFixedSize(true);
            binding.recyclerviewCart.setLayoutManager(new LinearLayoutManager(getContext()));
            //set total price
            int total = 0;
            for (int i = 0; i < carts.size(); i++) {
                total += Double.parseDouble(carts.get(i).getPrice()) * carts.get(i).getQuantity();
            }
            binding.tvTotalPrice.setText(total + " VND");
        });
    }
}