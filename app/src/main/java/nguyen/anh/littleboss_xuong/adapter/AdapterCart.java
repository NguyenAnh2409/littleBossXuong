package nguyen.anh.littleboss_xuong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.model.Cart;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {
    private List<Cart> carts;
    private Context context;

    public AdapterCart(List<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_one, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.tvName.setText(cart.getName());
        holder.tvPrice.setText(String.valueOf(cart.getPrice()));
        holder.tvQuantity.setText(cart.getQuantity());
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        ImageView imgMinus, imgPlus, imgProduct;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameProduct);
            tvPrice = itemView.findViewById(R.id.tvPriceProduct);
            tvQuantity = itemView.findViewById(R.id.tvNumber);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            checkBox = itemView.findViewById(R.id.checkbox_Cart);
        }
    }
}
