package com.example.up_buy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.up_buy.ItemClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

   private static final String Tag= "RecycleView";
   private Context mContext;
   private ArrayList<Ürün> ürünList;


    public RecycleAdapter(Context mContext, ArrayList<Ürün> ürünList) {
        this.mContext = mContext;
        this.ürünList = ürünList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{ // yapıcısı

       ImageView imageView;
       TextView textView;


       public ViewHolder(@NonNull View itemView) {
           super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.title);
       }
   }

    @NonNull
    @Override // view holderin başlatılması için method
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

         return new ViewHolder(view);
    }

    @Override  // verileri göstermek için method
    public void onBindViewHolder(@NonNull final ViewHolder holder,  int position) {

        final String s=ürünList.get(position).getTitle();
        final String s2=ürünList.get(position).getKategori();
        final String s3=ürünList.get(position).getÜrünfiyat();
        final String s4=ürünList.get(position).getTanım();
        final String s5=ürünList.get(position).getTitle();

        holder.textView.setText(ürünList.get(position).getTitle());
        Glide.with(mContext).load(ürünList.get(position).getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mContext, ProductDetailsActivity.class);

                intent.putExtra("ürünadi",s5);
                intent.putExtra("tanım",s4);
                intent.putExtra("kategori",s2);
                intent.putExtra("ürünfiyat",s3);
                intent.putExtra("tür",s);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ürünList.size();
    }
}
