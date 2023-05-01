package com.living.roomrental.tenant.activity.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationBarView;
import com.living.roomrental.R;
import com.living.roomrental.activity.profile.view.ViewProfileBottomSheet;
import com.living.roomrental.databinding.ActivityTenantMainBinding;
import com.living.roomrental.landlord.activity.fragments.FragmentController;
import com.living.roomrental.landlord.activity.fragments.chat.ChatLandlordFragment;
import com.living.roomrental.repository.local.SharedPreferenceStorage;
import com.living.roomrental.repository.local.SharedPreferencesController;
import com.living.roomrental.tenant.activity.fragments.home.AllPropertyFragment;
import com.living.roomrental.utilities.Validation;

import java.util.ArrayList;

public class TenantMainActivity extends AppCompatActivity {

    private ActivityTenantMainBinding binding;
    private ViewProfileBottomSheet bottomSheet;

    private FragmentController controller  = new FragmentController(this);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTenantMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
    }

    private void getDataFromLocal(){
        ArrayList<String> list ;
        list = SharedPreferenceStorage.getUserExtraData(SharedPreferencesController.getInstance(this).getPreferences());

        if(!Validation.isStringEmpty(list.get(1)))
            Glide.with(this).load(list.get(1)).into(binding.header.headerProfileImageView);
        else{
            binding.header.headerProfileImageView.setImageResource(R.drawable.ic_person);
        }
        binding.header.headerUserName.setText(list.get(0));
    }

    private void initListeners() {

        binding.header.headerProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheet = new ViewProfileBottomSheet();
                bottomSheet.show(getSupportFragmentManager(), "ViewProfileBottomSheet");
            }
        });

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if(fragment==null)
            controller.addFragment(new AllPropertyFragment(),R.id.fragmentContainer);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.myProperty :
                        controller.replaceFragment(new AllPropertyFragment(),R.id.fragmentContainer);
                        break;
                    case R.id.myRequest:

                        break;
                    case R.id.myChats:
                        controller.replaceFragment(new ChatLandlordFragment(),R.id.fragmentContainer);
                        break;
                    case R.id.myMore:
                        break;
                    default:
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromLocal();
        if(bottomSheet!=null){
            bottomSheet.dismiss();
        }
    }
}