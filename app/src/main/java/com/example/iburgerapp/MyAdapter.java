package com.example.iburgerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.* ;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<sOrder > order;
    int count1 = 0;

    public MyAdapter(List<sOrder> listData) {
        this.order = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_shape,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sOrder ld=order.get(position);
        holder.count.setText(String.valueOf(ld.getNum()));
        holder.name.setText(ld.getoName());
        holder.price.setText(String.valueOf(ld.getPrice()));
        holder.uid .setText(ld.getUserId());

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //count1= Integer.parseInt(holder.count.toString());
                String a=String.valueOf(holder.count.getText());
                count1= Integer.parseInt(a);
                count1++;
                holder.count.setText(String.valueOf(count1));
            }
        });

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=String.valueOf(holder.count.getText());
                count1= Integer.parseInt(a);
                if (count1 <= 0) {
                    count1 = 0;
                    holder.count.setText(String.valueOf(count1));
                } else {
                    count1--;
                    holder.count.setText(String.valueOf(count1));
                    }

            }
        });


    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView uid,name,price,count;
        private ImageView plus,minus;
        public ViewHolder(View itemView) {
            super(itemView);
            uid=(TextView)itemView.findViewById(R.id.idtxt);
            name=(TextView)itemView.findViewById(R.id.nametxt);
            count=(TextView)itemView.findViewById(R.id.counttxt );
            price=(TextView)itemView.findViewById(R.id.pricetxt);
            plus= (ImageView)itemView.findViewById(R.id.plus);
            minus= (ImageView)itemView.findViewById(R.id.minus);
        }
    }
}

