package nguyen.anh.littleboss_xuong.adapter;


import static nguyen.anh.littleboss_xuong.screen.MainActivity.productMain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.model.Product;
import nguyen.anh.littleboss_xuong.screen.MainActivity;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Detail;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Home;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private List<Product> list;
    private Fragment_Home context;

    public AdapterProduct(List<Product> list, Fragment_Home context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.item_one_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = list.get(position);
        holder.tvNameProduct.setText(product.getName());
        //format price to VND currency
        double price = Double.parseDouble(product.getPrice()) * 1000;
        holder.tvPriceProduct.setText(String.format("%,.0f", price) + " đ");
        Picasso.get().load(product.getImage()).into(holder.imgProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Fragment();
                Class fragmentClass = Fragment_Detail.class;
                productMain = product;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = context.getParentFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                            .replace(R.id.fragment_container, fragment).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameProduct, tvPriceProduct;
        ImageView imgProduct;
        public ViewHolder(View itemView) {
            super(itemView);
            tvNameProduct = itemView.findViewById(R.id.tvName_product);
            tvPriceProduct = itemView.findViewById(R.id.tvPrice_product);
            imgProduct = itemView.findViewById(R.id.img_product);
        }
    }
}
