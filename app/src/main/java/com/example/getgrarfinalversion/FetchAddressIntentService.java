package com.example.getgrarfinalversion;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class FetchAddressIntentService extends IntentService {
    private ResultReceiver resultReceiver;
    public FetchAddressIntentService(){
        super("FetchAddressIntentService");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent!= null ){

            String errorMassage="";
            resultReceiver= intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);
            Location location= intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);
            if (location== null){
                return;
            }
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses= null;
            try {
                addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),2);
            }catch (Exception exception){
                errorMassage=exception.getMessage();
            }
            if (addresses== null || addresses.isEmpty()){
                deliverResultToRecevier(Constants.FAILURE_RESULS,errorMassage);
            }else {
                Address address = addresses.get(0);
                ArrayList<String> addressFragments = new ArrayList<>();
                    for (int i = 0; i<= address.getMaxAddressLineIndex();i++){
                        addressFragments.add(address.getAddressLine(i));

                    }
                    deliverResultToRecevier(
                            Constants.SUCCESS_RESULT,
                            TextUtils.join(
                                    Objects.requireNonNull(System.getProperty("Line.separator")),
                                    addressFragments
                            )
                    );
            }
        }

    }
    private  void deliverResultToRecevier(int resultCode,String addressMassage){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.RESULS_DATA_KEY,addressMassage);
        resultReceiver.send( resultCode,bundle);
    }
}
