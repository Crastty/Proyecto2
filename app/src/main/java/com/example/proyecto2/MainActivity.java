package com.example.proyecto2;

import android.os.Bundle;

import com.example.proyecto2.Fragments.HomeFragment;
import com.example.proyecto2.Fragments.LoginFragment;
import com.example.proyecto2.Fragments.MapFragment;
import com.example.proyecto2.Fragments.PruebaFragment;
import com.example.proyecto2.Fragments.SearchFragment;
import com.example.proyecto2.Fragments.PlaceFragment;
import com.example.proyecto2.Model.PlaceModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        PlaceFragment.OnFragmentInteractionListener {

    Fragment myFragment;
    FragmentManager FM;
    FragmentTransaction FT;
    TextView tvUser;
    Button btLogout;
    public String userUID;
    public DatabaseReference myRef;
    FirebaseDatabase database;
    PlaceModel placeModel;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    myFragment = new HomeFragment();
                    FT.replace(R.id.frameLayout_container, myFragment);
                    FT.commit();
                    return true;

                case R.id.navigation_search:
                    myFragment = new SearchFragment();
                    FT.replace(R.id.frameLayout_container, myFragment);
                    FT.commit();
                    return true;

                case R.id.navigation_map:
                    myFragment = new PruebaFragment();
                    FT.replace(R.id.frameLayout_container, myFragment);
                    FT.commit();
                    return true;

                case R.id.add_place:
                    myFragment = new PlaceFragment();
                    FT.replace(R.id.frameLayout_container, myFragment);
                    FT.commit();
                    return true;

                case R.id.navigation_login:
                    myFragment = new LoginFragment();
                    FT.replace(R.id.frameLayout_container, myFragment);
                    FT.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        database = FirebaseDatabase.getInstance();

        tvUser = findViewById(R.id.tvUser);
        btLogout = findViewById(R.id.btLogout);

        if (savedInstanceState == null) {
            FM = getSupportFragmentManager();
            FT = FM.beginTransaction();
            myFragment = new LoginFragment();
            FT.replace(R.id.frameLayout_container, myFragment);
            FT.commit();
        }


        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                tvUser.setText("Usuario no registrado");
            }
        });
    }

    @Override
    public void onFragmentInteraction(String userLogin, String userUid) {

        if (userLogin == ""){
            tvUser.setText("Usuario sin registrar");
        } else {
            tvUser.setText(userLogin);
            myRef = database.getReference(userUid);
        }

        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        myFragment = new HomeFragment();
        FT.replace(R.id.frameLayout_container, myFragment);
        FT.commit();
    }

    @Override
    public void onFragmentInteraction(PlaceModel myPlaceModel) {

    }


}
