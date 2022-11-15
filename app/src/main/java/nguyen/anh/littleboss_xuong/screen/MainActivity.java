package nguyen.anh.littleboss_xuong.screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.databinding.ActivityMainBinding;
import nguyen.anh.littleboss_xuong.model.Category;
import nguyen.anh.littleboss_xuong.model.Product;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Cart;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Detail;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Heart;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Home;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_User;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static Fragment fragment1 = null;
    public static Product productMain = new Product();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.navigation.add(new MeowBottomNavigation.Model(1, R.drawable.iconhome));
        binding.navigation.add(new MeowBottomNavigation.Model(2, R.drawable.iconheart));
        binding.navigation.add(new MeowBottomNavigation.Model(3, R.drawable.iconcart));
        binding.navigation.add(new MeowBottomNavigation.Model(4, R.drawable.iconuser));
        if(getIntent().getIntExtra("check",0) == 1) {
            fragment1 = new Fragment_Detail();
            loadFragment(fragment1);
            return;
        }
        binding.navigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                // your codes
                switch (item.getId()) {
                    case 1:
                        fragment1 = new Fragment_Home();
                        break;
                    case 2:
                        fragment1 = new Fragment_Heart();
                        break;
                    case 3:
                        fragment1 = new Fragment_Cart();
                        break;
                    case 4:
                        fragment1 = new Fragment_User();
                        break;
                }
                loadFragment(fragment1);
            }
        });

        binding.navigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
               if(item.getId() == 1){
                   new MeowBottomNavigation.Model(1, R.drawable.iconhome);
                   new MeowBottomNavigation.Model(2, R.drawable.iconheart);
                   new MeowBottomNavigation.Model(3, R.drawable.iconcart);
                   new MeowBottomNavigation.Model(4, R.drawable.iconuser);
               }
                if(item.getId() == 2){
                     new MeowBottomNavigation.Model(1, R.drawable.iconhome2);
                     new MeowBottomNavigation.Model(2, R.drawable.iconheart2);
                     new MeowBottomNavigation.Model(3, R.drawable.iconcart);
                     new MeowBottomNavigation.Model(4, R.drawable.iconuser);
                }
                if(item.getId() == 3){
                    new MeowBottomNavigation.Model(1, R.drawable.iconhome2);
                    new MeowBottomNavigation.Model(2, R.drawable.iconheart);
                    new MeowBottomNavigation.Model(3, R.drawable.iconcart2);
                    new MeowBottomNavigation.Model(4, R.drawable.iconuser);
                }
                if(item.getId() == 4){
                    new MeowBottomNavigation.Model(1, R.drawable.iconhome2);
                    new MeowBottomNavigation.Model(2, R.drawable.iconheart);
                    new MeowBottomNavigation.Model(3, R.drawable.iconcart);
                    new MeowBottomNavigation.Model(4, R.drawable.iconuser2);
                }
            }
        });
        binding.navigation.show(1,true);
        if(savedInstanceState == null){
            fragment1 = new Fragment_Home();
            loadFragment(fragment1);
        }

        binding.navigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}