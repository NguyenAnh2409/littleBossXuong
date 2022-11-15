package nguyen.anh.littleboss_xuong.screen.fragment;

import static nguyen.anh.littleboss_xuong.screen.MainActivity.productMain;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.Format;
import java.util.List;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.databinding.FragmentDetailBinding;
import nguyen.anh.littleboss_xuong.model.Cart;


public class Fragment_Detail extends Fragment {

    FragmentDetailBinding binding;
    public static MutableLiveData<List<Cart>> cartMutableLiveData = new MutableLiveData<>();
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

        binding.imgDetailBack.setOnClickListener(v->onClickBack());

        binding.btnAddCart.setOnClickListener(v->onClickAddCart());
        fillData();
    }

    private void onClickAddCart() {
        Cart cart = new Cart(productMain.get_id(),productMain.getName(),productMain.getImage(),Integer.parseInt(binding.tvNumber.getText().toString()),productMain.getPrice());
        cartMutableLiveData.observe(getViewLifecycleOwner(),carts -> {
            carts.add(cart);
            cartMutableLiveData.setValue(carts);
        });
        Toast.makeText(getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
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
    }
}