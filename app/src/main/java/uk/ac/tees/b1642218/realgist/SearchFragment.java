package uk.ac.tees.b1642218.realgist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class SearchFragment extends Fragment implements OnMapReadyCallback {
    String TAG = SearchFragment.class.getSimpleName();
    boolean checkPermission;
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        verifyUserPermission();
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (checkPermission) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFragment);
            ;
            if (supportMapFragment == null) {
                SupportMapFragment fragment = SupportMapFragment.newInstance();
                fragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFragment);
            }


            supportMapFragment.getMapAsync(this);
        }
    }

    private void verifyUserPermission() {
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                checkPermission = true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        Log.d(TAG, String.valueOf(map));
        map.setMyLocationEnabled(true);
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}


