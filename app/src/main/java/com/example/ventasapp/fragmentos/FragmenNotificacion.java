package com.example.ventasapp.fragmentos;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventasapp.R;
import com.example.ventasapp.adaptadores.NotificacionesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmenNotificacion extends Fragment {
    private RecyclerView recyclerView;
    private NotificacionesAdapter studentAdapter;
    private List studentDataList = new ArrayList<>();
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notificacion_fragment, container, false);


        recyclerView = view.findViewById(R.id.reciclerHorizontal);
        studentAdapter = new NotificacionesAdapter(studentDataList,getActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(studentAdapter);
        StudentDataPrepare();


        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void StudentDataPrepare() {
        studentData data = new studentData("sai", 25);
        studentDataList.add(data);
        data = new studentData("sai raj", 25);
        studentDataList.add(data);
        data = new studentData("raghu", 20);
        studentDataList.add(data);
        data = new studentData("raj", 28);
        studentDataList.add(data);
        data = new studentData("amar", 15);
        studentDataList.add(data);
        data = new studentData("bapu", 19);
        studentDataList.add(data);
        data = new studentData("chandra", 52);
        studentDataList.add(data);
        data = new studentData("deraj", 30);
        studentDataList.add(data);
        data = new studentData("eshanth", 28);
        studentDataList.add(data);
        Collections.sort(studentDataList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }


        });
    }



    FragmenNotificacion(){

    }



    public static class studentData {

        public  String name;
        public int age;

        public studentData(String name, int age) {
        this.name = name;
        this.age = age;
        }
    }
}