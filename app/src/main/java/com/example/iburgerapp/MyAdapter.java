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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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


                update(name,count1,pric);

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
                    holder.rPrice.setText(String.valueOf(0));
                    //update(name,0, 0);
                }
                else {
                    p1 =  String.valueOf(holder.price.getText());
                    count1--;
                    holder.count.setText(String.valueOf(count1));
                    String p =  String.valueOf(holder.rPrice.getText());
                    pric=  Integer.parseInt(p)-Integer.parseInt(p1);
                    holder.rPrice.setText(String.valueOf(pric));
                    update(name,count1,pric);
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

    public void update(String name, int num, int price) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query delete = ref.child("Order").orderByChild("userId").equalTo(userId);

        delete.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                    sOrder s = deleteSnapshot.getValue(sOrder.class);
                    if(s.getType().equals("Snack")){
                        if (s.getoName().equals(name) ){
                            order.clear();
                            Map<String, Object> map = new HashMap<>();
                            map.put("/Order/" + deleteSnapshot.getKey() + "/rPrice/", price);//price
                            map.put("/Order/" + deleteSnapshot.getKey() + "/num/", num);//count1
                            ref.updateChildren(map);

                        }

                    }
                    if (s.getType().equals("Burger")){
                        if (s.getoName().equals(name) ){
                            order.clear();
                            Map<String, Object> map = new HashMap<>();
                            map.put("/Order/" + deleteSnapshot.getKey() + "/rPrice/", price);//price
                            map.put("/Order/" + deleteSnapshot.getKey() + "/num/", num);//count1
                            ref.updateChildren(map);

                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

           }
}

