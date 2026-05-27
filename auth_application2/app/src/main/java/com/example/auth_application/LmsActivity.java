package com.example.auth_application;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.auth_application.databinding.ActivityLmsBinding;


public class LmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLmsBinding binding = ActivityLmsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       binding.tab.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id==R.id.home){
                    getDesign(new Homefragment());
                }
           if (id==R.id.book){
               getDesign(new Coursefragment());
           }
           if (id==R.id.help){
               getDesign(new Helpfragment());
           }
                   return true;

       });




//       drawer coding start
        ImageButton bar = findViewById(R.id.bariconbtn);
        DrawerLayout drawer = findViewById(R.id.leftdrawer);
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.open();


            }
        });

//        drawar coding end
    }
    private void getDesign(Fragment ui){
           FragmentManager mange = getSupportFragmentManager();
           FragmentTransaction transition = mange.beginTransaction();
           transition.replace(R.id.tabview,ui);
           transition.commit();

    }
}