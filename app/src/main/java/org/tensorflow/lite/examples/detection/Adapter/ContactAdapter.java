package org.tensorflow.lite.examples.detection.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.detection.Message_and_Dial;
import org.tensorflow.lite.examples.detection.R;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    ArrayList contact;
    ArrayList contact_num;
    Context context;

    public ContactAdapter(ArrayList contact, ArrayList contact_num, Context context) {
        this.contact = contact;
        this.contact_num = contact_num;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.content_contacts_re, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.contact_name.setText((CharSequence) contact.get(position));
        holder.contact_num.setText((CharSequence) contact_num.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(context, alphabets.get(position)+" selected", Toast.LENGTH_SHORT).show();
//                Intent intent = (context).getIntent();
//                intent.putExtra("SELECTED_PAYMENT", contact_num.get(position).toString());
//                context.startActivity(new Intent(context, Message_and_Dial.class));

                Intent intent=new Intent(context, Message_and_Dial.class);
                intent.putExtra("Contact Name", contact.get(position).toString());
                intent.putExtra("Phone Number", contact_num.get(position).toString());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return contact.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contact_name,contact_num;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            contact_name = itemView.findViewById(R.id.contact_name);
            contact_num = itemView.findViewById(R.id.contact_num);



        }
    }
}

