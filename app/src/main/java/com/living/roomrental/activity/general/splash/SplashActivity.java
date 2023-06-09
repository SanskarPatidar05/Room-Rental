package com.living.roomrental.activity.general.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.living.roomrental.activity.auth.login.LoginActivity;
import com.living.roomrental.databinding.ActivitySplashBinding;
import com.living.roomrental.landlord.activity.main.LandlordMainActivity;
import com.living.roomrental.repository.local.SharedPreferenceStorage;
import com.living.roomrental.repository.local.SharedPreferencesController;
import com.living.roomrental.tenant.activity.main.TenantMainActivity;
import com.living.roomrental.utilities.AppBoiler;
import com.living.roomrental.utilities.AppConstants;
import com.living.roomrental.utilities.Validation;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = SharedPreferencesController.getInstance(SplashActivity.this).getPreferences();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String whoIsUser = SharedPreferenceStorage.getWhoIsUser(preferences);

                if(Validation.isStringEmpty(SharedPreferenceStorage.getUidOfUser(preferences)) || Validation.isStringEmpty(whoIsUser)){

                    AppBoiler.navigateToActivityWithFinish(SplashActivity.this, LoginActivity.class,null);
                }else if(whoIsUser.equals(AppConstants.LANDLORD)){

                    AppBoiler.navigateToActivityWithFinish(SplashActivity.this, LandlordMainActivity.class,null);
                }else if(whoIsUser.equals(AppConstants.TENANT)){

                    AppBoiler.navigateToActivityWithFinish(SplashActivity.this, TenantMainActivity.class,null);
                }
            }
        },2000);

    }

}

//========= WhoIsUser is to check from database that the user is tenant or landlord or not defined yet==========
// uid will check the user is logged in or not