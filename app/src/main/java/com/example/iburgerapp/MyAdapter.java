package com.example.iburgerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<sOrder > order;
    int count1 = 0;
    int pric ;
    String p1;
    String userId;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();


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
        holder.rPrice .setText(String.valueOf(ld.getrPrice()));
        holder.price.setText(String.valueOf(ld.getPrice()));
        holder.uid .setText(ld.getUserId());

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userId= currentFirebaseUser.getUid();



        holder.plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                p1 =  String.valueOf(holder.price.getText());

                String name = String.valueOf(holder.name.getText());

                String a = String.valueOf(holder.count.getText());
                count1 = Integer.parseInt(a);
                count1++;
                holder.count.setText(String.valueOf(count1));
                String p = String.valueOf(holder.rPrice.getText());
                pric = Integer.parseInt(p) + Integer.parseInt(p1);
                holder.rPrice.setText(String.valueOf(pric));

                if (name.equals("S H A W E R M A") || name.equals("S H A W E R M A M E A L") ){
                    String f= "s"+userId;
                    update(f,pric,count1);
                    }
                if (name.equals("H O T  D O G") || name.equals("H O T  D O G M E A L") ){
                    String f= "h"+userId;
                    update(f,pric,count1);
                    }

                if (name.equals("C H R I S P Y") || name.equals("C H R I S P Y M E A L") ){
                    String f= "c"+userId;
                    update(f,pric,count1);
                    }

                if (name.equals("F A H E T A") || name.equals("F A H E T A M E A L") ){
                    String f= "f"+userId;
                    update(f,pric,count1);
                    }
            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1 =  String.valueOf(holder.price.getText());

                String name = String.valueOf(holder.name.getText());
                String a=String.valueOf(holder.count.getText());
                count1= Integer.parseInt(a);
                if (count1 <= 0 ) {
                    count1 = 0;
                    holder.count.setText(String.valueOf(count1));
                    holder.rPrice.setText(String.valueOf(0));}
                else {
                    p1 =  String.valueOf(holder.price.getText());
                    count1--;
                    holder.count.setText(String.valueOf(count1));
                    String p =  String.valueOf(holder.rPrice.getText());
                    pric=  Integer.parseInt(p)-Integer.parseInt(p1);
                    holder.rPrice.setText(String.valueOf(pric));
                    if (name.equals("S H A W E R M A") || name.equals("S H A W E R M A M E A L") ){
                        String f= "s"+userId;
                        update(f,pric,count1);
                    }
                    if (name.equals("H O T  D O G") || name.equals("H O T  D O G M E A L") ){
                        String f= "h"+userId;
                        update(f,pric,count1);
                    }

                    if (name.equals("C H R I S P Y") || name.equals("C H R I S P Y M E A L") ){
                        String f= "c"+userId;
                        update(f,pric,count1);
                    }

                    if (name.equals("F A H E T A") || name.equals("F A H E T A M E A L") ){
                        String f= "f"+userId;
                        update(f,pric,count1);
                    }

                    }

            }
        });


    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView uid,name,price,count,rPrice;
        private ImageView plus,minus;
        public ViewHolder(View itemView) {
            super(itemView);
            uid=(TextView)itemView.findViewById(R.id.idtxt);
            name=(TextView)itemView.findViewById(R.id.nametxt);
            count=(TextView)itemView.findViewById(R.id.counttxt );
            price=(TextView)itemView.findViewById(R.id.pricetxt);
            plus= (ImageView)itemView.findViewById(R.id.plus);
            minus= (ImageView)itemView.findViewById(R.id.minus);
            rPrice= (TextView)itemView.findViewById(R.id.rPrice);


        }
    }

    public void update(String f , int p , int c) {
        order.clear();

        Map<String, Object> map = new HashMap<>();
        map.put("/Snack/" + f + "/rPrice/", p);//price
        map.put("/Snack/" + f + "/num/", c);//count1

        ref.updateChildren(map);

        //ref.child("Snack").child(f).child("rPrice").setValue(pric);
        //ref.child("Snack").child(f).child("num").setValue(count1);

           }
}

