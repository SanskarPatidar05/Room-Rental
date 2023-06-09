package com.living.roomrental.tenant.activity.fragments.request;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.living.roomrental.FirebaseController;
import com.living.roomrental.activity.profile.model.ProfileModel;
import com.living.roomrental.landlord.activity.fragments.request.MyRequestsModel;
import com.living.roomrental.tenant.activity.view.PropertyRequestModel;
import com.living.roomrental.utilities.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MyRequestTenantRepository {

    private DatabaseReference databaseReference;
    private String uid;

    private MutableLiveData<List<MyRequestTenantModel>> myRequestTenantMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<MyRequestTenantPropertyModel>> myRequestTenantPropertyMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<List<ProfileModel>> profileModelMutableLiveData = new MutableLiveData<>();

    public MyRequestTenantRepository(){
        databaseReference = FirebaseController.getInstance().getDatabaseReference();
        uid = FirebaseAuth.getInstance().getUid();
    }

    /* ============================ PROPERTIES KEYS WHERE TENANT REQUESTED ========================*/

    public MutableLiveData<List<MyRequestTenantModel>> getTenantRequests(){

        databaseReference.child("UserRequests").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<MyRequestTenantModel> myRequestTenantModelArrayList = new ArrayList<>();

                if(snapshot.exists()){

                    for(DataSnapshot propertyKeySnapshot : snapshot.getChildren()){
                        MyRequestTenantModel model = propertyKeySnapshot.getValue(MyRequestTenantModel.class);
                        myRequestTenantModelArrayList.add(model);
                    }
                    System.out.println("=============== SIZE ========== "+myRequestTenantModelArrayList.size());
                }
                myRequestTenantMutableLiveData.setValue(myRequestTenantModelArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myRequestTenantMutableLiveData.setValue(null);
                System.out.println("============= ERROR =============== "+error.getMessage());
            }
        });
        return myRequestTenantMutableLiveData;
    }



    /* ============================ GET PROPERTY DETAILS TO SHOW TENANT ===========================*/

    public MutableLiveData<List<MyRequestTenantPropertyModel>> getPropertyDetailsFromPropertyKeys(List<MyRequestTenantModel> myRequestTenantModelArrayList){

        ArrayList<MyRequestTenantPropertyModel> modelArrayList = new ArrayList<>();
        for(MyRequestTenantModel model : myRequestTenantModelArrayList){

            databaseReference.child(AppConstants.LANDLORD_PROPERTY).child(model.getUidOfLandlord()).child(model.getPropertyKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        System.out.println("============= SNAPSHOT ============ "+modelArrayList.size());

                        MyRequestTenantPropertyModel model1 = snapshot.getValue(MyRequestTenantPropertyModel.class);
                        model1.setPropertyRequests(snapshot.child("propertyRequests").child(uid).getValue(PropertyRequestModel.class));
                        model1.setStatus(model.getStatus());
                        model1.setLandlordId(model.getUidOfLandlord());
                        modelArrayList.add(model1);
                    } else {
                        System.out.println("============= NO SNAPSHOT ============ "+modelArrayList.size());
                    }

                    if(myRequestTenantModelArrayList.size() == modelArrayList.size()){
                        myRequestTenantPropertyMutableLiveData.setValue(modelArrayList);
                        System.out.println("============= TOTAL LIST ============ "+modelArrayList.size());
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("================ ERROR ============== "+error.getMessage());
                    myRequestTenantPropertyMutableLiveData.setValue(null);
                }
            });
        }
       return myRequestTenantPropertyMutableLiveData;
    }


    public MutableLiveData<List<ProfileModel>> getProfileDataFromServer(List<MyRequestTenantPropertyModel> myRequestTenantPropertyModels) {

        DatabaseReference databaseReference = FirebaseController.getInstance().getDatabaseReference().child(AppConstants.USER_PROFILE);

        List<ProfileModel> profileModels = new ArrayList<>();
        for( int i=0 ; i< myRequestTenantPropertyModels.size() ; i++) {

            databaseReference.child(myRequestTenantPropertyModels.get(i).getLandlordId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        ProfileModel model = snapshot.getValue(ProfileModel.class);
                        System.out.println("================== 112"+model.toString());
                        profileModels.add(model);

                        System.out.println("================== 112"+profileModels.size()+"   "+myRequestTenantPropertyModels.size());
                        if(profileModels.size() == myRequestTenantPropertyModels.size()){
                            System.out.println("==================hhhhhhhhhhhh");
                            profileModelMutableLiveData.setValue(profileModels);
                        }

                        System.out.println("model size"+profileModels.size());
                    } else {
                        System.out.println("================== 221");
                        profileModelMutableLiveData.setValue(null);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("=========== Error ================" + error.getMessage());
                }
            });
        }

        return profileModelMutableLiveData;
    }
}
