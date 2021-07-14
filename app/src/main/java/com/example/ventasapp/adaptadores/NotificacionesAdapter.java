package com.example.ventasapp.adaptadores;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.ventasapp.R;
import com.example.ventasapp.fragmentos.FragmenNotificacion;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

import static androidx.recyclerview.widget.RecyclerView.*;

public class NotificacionesAdapter extends Adapter {
    List studentDataList;
    public TextView name,age;
    LinearLayout parent;
    ImageView img;
    public NotificacionesAdapter(List studentDataList, FragmentActivity activity) {
        this.studentDataList = studentDataList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recicler_imagenes, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder viewHolder, int position) {
        FragmenNotificacion.studentData data = (FragmenNotificacion.studentData) studentDataList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
       parent.setBackgroundColor(currentColor);
       name.setText(data.name);
       age.setText(String.valueOf(data.age));
    }


    @Override
    public int getItemCount() {
        return studentDataList.size();
    }
   public class MyViewHolder extends ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView6);
            parent = itemView.findViewById(R.id.parent);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
        }
    }
}
