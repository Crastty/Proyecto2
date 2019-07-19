package com.example.proyecto2.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.proyecto2.Firebase.MyFirebase;
import com.example.proyecto2.MainActivity;
import com.example.proyecto2.Model.PlaceModel;
import com.example.proyecto2.R;

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceFragment extends Fragment {

    ImageView ivShowPhoto;
    EditText etNamePhoto, etObservations;
    Button btSavePhoto;
    PlaceModel myPalceModel;
    private OnFragmentInteractionListener myListener;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;


    public PlaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myPalceModel = new PlaceModel();

        View view = inflater.inflate(R.layout.fragment_place, container, false);

        ivShowPhoto = view.findViewById(R.id.ivFoto);
        etNamePhoto = view.findViewById(R.id.etNombreFoto);
        etObservations = view.findViewById(R.id.etObservation);
        btSavePhoto = view.findViewById(R.id.btGuardarFoto);

        ivShowPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Es una versión mayor que la 6.0
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
                else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        btSavePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPalceModel.setTitle(etNamePhoto.getText().toString());
                myPalceModel.setObservation(etObservations.getText().toString());
                if (myPalceModel.getTitle()!= null){
                    MyFirebase firebase = new MyFirebase();
                    firebase.addFirebase(myPalceModel, ((MainActivity) getActivity()).myRef);
                    ivShowPhoto.setImageResource(R.drawable.baseline_photo_camera_black_48);
                    etNamePhoto.setText("");
                    etObservations.setText("");
                } else {
                    etNamePhoto.setError("Nombre no puede ser vacio");
                }

                /*Double lat = ((MainActivity) getActivity()).mLatitude;
                myPalceModel.setLatitude(lat);
                Double lon = ((MainActivity) getActivity()).mLongitude;
                myPalceModel.setLongitude(lon);*/

            }
        });



        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permisos de la cámara concedidos", Toast.LENGTH_LONG).show();

                // LANZO LA CÁMARA DE FORMA NATIVA
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else {
                Toast.makeText(getActivity(), "Permisos de la cámara denegados", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");  // CREO UN BITMAP A PARTIR DE LOS DATOS RECOGIDOS POR LA CÁMARA

            String stringBitmap = BitMapToString(photo);
            myPalceModel.setImagen(stringBitmap);
            ivShowPhoto.setImageBitmap(photo);
        }
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginFragment.OnFragmentInteractionListener) {
            myListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        myListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(PlaceModel myPlaceModel);
    }

}
