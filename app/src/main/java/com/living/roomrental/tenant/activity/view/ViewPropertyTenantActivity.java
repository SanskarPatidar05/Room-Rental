package com.living.roomrental.tenant.activity.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.living.roomrental.DatePickerInterface;
import com.living.roomrental.DialogListener;
import com.living.roomrental.R;
import com.living.roomrental.databinding.ActivityViewPropertyTenantBinding;
import com.living.roomrental.landlord.activity.create_property.CreatePropertyDataModel;
import com.living.roomrental.landlord.activity.view_property.ViewPropertyImageAdapter;
import com.living.roomrental.utilities.AppBoiler;
import com.living.roomrental.utilities.AppConstants;
import com.living.roomrental.utilities.DateTimeFormatter;
import com.living.roomrental.utilities.Validation;

public class ViewPropertyTenantActivity extends AppCompatActivity {

    private ActivityViewPropertyTenantBinding binding;
    private CreatePropertyDataModel model;
    private String description;

    private Dialog progressDialog , responseDialog;

    private ViewPropertyTenantViewModel viewPropertyTenantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewPropertyTenantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.header.headerTitle.setText("Book Property");


        getBundles();
        if(model.getBookingStatus().equals("vacant"))
           isUserRequestedForProperty();
        initListeners();
        setDataToViews();
    }

    private void getBundles() {
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
           model =  bundle.getParcelable("data");
           viewPropertyTenantViewModel = new ViewModelProvider(this ,new ViewPropertyTenantViewModelFactory(model.getKey(),model.getUid())).get(ViewPropertyTenantViewModel.class);
        }
    }

    private void initListeners(){

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendRequestToLandlordDialog();
            }
        });
    }
    private void setDataToViews(){

        if(model.getPropertyImages().size()>0){
            ViewPropertyImageAdapter adapter = new ViewPropertyImageAdapter(this,model.getPropertyImages());

            binding.propertyImageRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
            binding.propertyImageRecyclerView.setAdapter(adapter);
        }

        binding.propertyNameData.setText(model.getPropertyName());
        binding.addressData.setText(model.getMapLocationAddress());
        binding.landmarkData.setText(model.getLandmarkAddress());
        binding.propertyTypeData.setText(model.getType());
        binding.propertyForData.setText(model.getPeopleFor());
        binding.propertySizeData.setText(model.getSize());
        binding.propertyAgreementData.setText(model.getAgreement());
        binding.propertyFacilitiesData.setText(model.getFacilities().toString());
        binding.propertyFurnishingData.setText(model.getFurnishing());
        binding.furnishingDescriptionData.setText(model.getFurnishingDescription());
        binding.propertyRentData.setText(model.getRent());
        binding.propertyDescriptionData.setText(model.getDescription());

        if(!model.getBookingStatus().equals("vacant")){
           binding.bookBtn.setVisibility(View.GONE);
        }
    }
    private void sendRequestToLandlordDialog(){

        Dialog sendRequestDialog = new Dialog(this);
        sendRequestDialog.setContentView(R.layout.layout_send_request_for_book_dialog);

        Window dialogWindow = sendRequestDialog.getWindow();
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable insetDrawable = new InsetDrawable(colorDrawable,20);
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setBackgroundDrawable(insetDrawable);

        TextInputLayout selectDateTextInputLayout = sendRequestDialog.findViewById(R.id.selectDateTextInputLayout);
        TextInputEditText descriptionEditText , selectDateEditText;
        descriptionEditText= sendRequestDialog.findViewById(R.id.descriptionEditText);
        selectDateEditText= sendRequestDialog.findViewById(R.id.selectDateEditText);

        MaterialButton button = sendRequestDialog.findViewById(R.id.sendRequestBtn);
        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                description = editable.toString();
            }
        });

        selectDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //todo -> open calender - pick date - setText date to picked date
                DateTimeFormatter.selectDateDialog(ViewPropertyTenantActivity.this, new DatePickerInterface() {
                    @Override
                    public void onSelectDate(String date) {
                        selectDateEditText.setText(date);
                        viewPropertyTenantViewModel.setDate(date);
                        AppBoiler.setInputLayoutErrorDisable(selectDateTextInputLayout);
                    }
                });
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Validation.isStringEmpty(viewPropertyTenantViewModel.getDate())){
                    sendRequestDialog.dismiss();

                    if (AppBoiler.isInternetConnected(ViewPropertyTenantActivity.this)) {
                        sendRequestToLandlord();
                    } else {
                        AppBoiler.showSnackBarForInternet(ViewPropertyTenantActivity.this,binding.rootLayoutOfViewProperty);
                    }
                } else {
                    selectDateTextInputLayout.setError("Please select date");
                 }


            }
        });
        sendRequestDialog.show();
    }

    private void isUserRequestedForProperty(){
        LiveData<Boolean> isUserRequestLiveData = viewPropertyTenantViewModel.isUserRequested();

        isUserRequestLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == true){
                    binding.bookBtn.setText("Already Requested");
                    binding.bookBtn.setBackgroundColor(getColor(R.color.grey_300));
                    binding.bookBtn.setEnabled(false);
                }
            }
        });
    }

    private void sendRequestToLandlord(){

        progressDialog = AppBoiler.setProgressDialog(this);
        PropertyRequestModel propertyRequestModel = new PropertyRequestModel(null,description, viewPropertyTenantViewModel.getDate());

        LiveData<String> responseLiveData = viewPropertyTenantViewModel.sendRequestToLandlord(propertyRequestModel) ; //sendRequest(,model.getKey(),model.getUid());

        responseLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if(s.equals(AppConstants.SUCCESS)){
                    storeRequestDataToServer();

                } else {
                    progressDialog.dismiss();
                    System.out.println("========= ERROR 1 =====");
                }
            }
        });
    }

    private void storeRequestDataToServer(){

        LiveData<String> responseLiveData = viewPropertyTenantViewModel.storeRequestDataToServer();

        responseLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();
                if(s.equals(AppConstants.SUCCESS)){

                    responseDialog = AppBoiler.customDialogWithBtn(ViewPropertyTenantActivity.this,"Request sent successfully", R.drawable.ic_done, new DialogListener() {
                        @Override
                        public void onClick() {
                            responseDialog.dismiss();
                            onBackPressed();
                        }
                    });

                } else {

                    responseDialog = AppBoiler.customDialogWithBtn(ViewPropertyTenantActivity.this, s, R.drawable.ic_error, new DialogListener() {
                        @Override
                        public void onClick() {
                            responseDialog.dismiss();
                        }
                    });
                    System.out.println("========= ERROR 1 =====");
                }
            }
        });
    }
}