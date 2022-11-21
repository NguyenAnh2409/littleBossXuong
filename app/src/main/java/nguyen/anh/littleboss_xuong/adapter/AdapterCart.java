package nguyen.anh.littleboss_xuong.adapter;

import static nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Cart.newInstance;
import static nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Cart.totalCart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.model.Cart;
import nguyen.anh.littleboss_xuong.network.IRetrofitService;
import nguyen.anh.littleboss_xuong.network.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = carts.get(position);
        holder.tvName.setText(cart.getName());
        double price = Double.parseDouble(cart.getPrice()) * cart.getQuantity() * 1000;
        holder.tvPrice.setText(String.format("%,.0f", price));
        holder.tvQuantity.setText(cart.getQuantity()+"");
        Picasso.get().load(cart.getImage()).into(holder.imgProduct);
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Bạn có chắc chắn muốn xóa sản phẩm này?")
                        .setConfirmText("Xóa")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                IRetrofitService iRetrofitService = RetrofitBuilder.createService(IRetrofitService.class);
                                iRetrofitService.deleteCart(cart.get_id()).enqueue(new Callback<Map<String, Object>>() {
                                    @Override
                                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                                        if (response.isSuccessful()) {
                                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Xóa thành công")
                                                    .show();
                                            carts.remove(position);
                                            notifyDataSetChanged();
                                            //chạy lại fragment cart
                                            newInstance().updateTotal(carts);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Xóa thất bại "+t.getMessage())
                                                .show();
                                    }
                                });
                            }
                        })
                        .setCancelButton("Hủy", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
//        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int quantity = cart.getQuantity();
//                quantity++;
//                cart.setQuantity(quantity);
//                holder.tvQuantity.setText(quantity+"");
//                double price = Double.parseDouble(cart.getPrice()) * quantity * 1000;
//                cart.setPrice(String.format("%,.0f", price));
//                holder.tvPrice.setText(String.format("%,.0f", price));
//                IRetrofitService iRetrofitService = RetrofitBuilder.createService(IRetrofitService.class);
//                iRetrofitService.updateCart(cart.get_id(),cart.getReceipt_id(),cart.getProduct_id(),cart.getQuantity(),price).enqueue(new Callback<Map<String, Object>>() {
//                    @Override
//                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
//                        if (response.isSuccessful()) {
//                            newInstance().updateTotal(carts);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
//
//                    }
//                });
//            }
//        });
//        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int quantity = cart.getQuantity();
//                if (quantity > 1) {
//                    quantity--;
//                    cart.setQuantity(quantity);
//                    holder.tvQuantity.setText(quantity+"");
//                    double price = Double.parseDouble(cart.getPrice()) * quantity * 1000;
//                    cart.setPrice(price+"");
//                    holder.tvPrice.setText(String.format("%,.0f", price));
//                    IRetrofitService iRetrofitService = RetrofitBuilder.createService(IRetrofitService.class);
//                    iRetrofitService.updateCart(cart.get_id(),cart.getReceipt_id(),cart.getProduct_id(),cart.getQuantity(),price).enqueue(new Callback<Map<String, Object>>() {
//                        @Override
//                        public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
//                            if (response.isSuccessful()) {
//                                newInstance().updateTotal(carts);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Map<String, Object>> call, Throwable t) {
//
//                        }
//                    });
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity, tvDelete;
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
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }
}
