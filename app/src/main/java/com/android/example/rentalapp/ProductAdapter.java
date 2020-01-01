package com.android.example.rentalapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends FirestoreRecyclerAdapter<ProductItem, ProductAdapter.ProductViewHolder> {

            public ProductAdapter(@NonNull FirestoreRecyclerOptions<ProductItem> options) {
                super(options);

        }

        @Override
        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductItem model) {

            Picasso.get().load(model.getImage()).fit().into(holder.pImage);
            holder.pName.setText( model.getProductName());
            holder.pRentalPrice.setText("\u20B9" + " " + model.getRentalPrice());
            holder.pTime.setText("Available for " + model.getAvailabilityDuration() + " days");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DetailsActivity.class);

                    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                    DocumentReference ref = fStore.collection("products").document();
                    i.putExtra("Product ID", ref.getId()); // passing the product ID to details activity
                    v.getContext().startActivity(i);
                }
            });
            }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_recycler_view_items,
                    parent, false);
            return new ProductViewHolder(v);
        }

        class ProductViewHolder extends RecyclerView.ViewHolder {
            private ImageView pImage;
            private TextView pName, pRentalPrice, pTime;



            private ProductViewHolder(View itemView) {
                super(itemView);
                pImage = itemView.findViewById(R.id.image_view_product);
                pName = itemView.findViewById(R.id.text_view_pName);
                pRentalPrice = itemView.findViewById(R.id.text_view_pRentalPrice);
                pTime = itemView.findViewById(R.id.text_view_pAvailabilityDuration);


            }
        }
    }
