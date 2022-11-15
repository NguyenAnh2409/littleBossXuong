package nguyen.anh.littleboss_xuong.adapter;

import static nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Home.instans;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nguyen.anh.littleboss_xuong.R;
import nguyen.anh.littleboss_xuong.model.Category;
import nguyen.anh.littleboss_xuong.screen.fragment.Fragment_Home;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {
    private List<Category> list;
    private Fragment_Home context;
    private boolean isChABoolean = true;
    public AdapterCategory(List<Category> list, Fragment_Home context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.item_caterory_one, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = list.get(position);
        holder.tvName.setText(category.getName());
        if(category.isCh()){
            holder.linearLayout.setBackgroundResource(R.drawable.bg_item_caterogy);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.white));
        }
        else {
            holder.linearLayout.setBackgroundResource(R.drawable.bg_item_caterogy2);
            holder.tvName.setTextColor(context.getResources().getColor(R.color.black));
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category.isCh()) {
                    category.setCh(false);
                    holder.linearLayout.setBackgroundResource(R.drawable.bg_item_caterogy);
                    holder.tvName.setTextColor(context.getResources().getColor(R.color.white));
                }else {
                    list.get(position).setCh(true);
                    if(list.get(position).isCh()){
                        holder.linearLayout.setBackgroundResource(R.drawable.bg_item_caterogy2);
                        notifyDataSetChanged();
                    }else{
                        holder.linearLayout.setBackgroundResource(R.drawable.bg_item_caterogy);
                    }
                }
                for(int i = 0; i < list.size(); i++){
                    if(i != position){
                        list.get(i).setCh(false);
                    }
                }
                context.updateProduct(list.get(position).get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCaterogy);
            linearLayout = itemView.findViewById(R.id.linear_item_caterogy);
        }
    }
}
