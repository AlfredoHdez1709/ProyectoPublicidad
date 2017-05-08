package com.ahrsoft.movil.proyectofood.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahrsoft.movil.proyectofood.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class DetailPublicActivity extends AppCompatActivity  {

    private String mPost_key = null;
    private DatabaseReference mDatabase;
    private ImageView mBlogSingleImage;
    private TextView mBlogSingleDesc;
    private TextView mBlogHorario;
    private Button mBlogStatus;
    private CircularImageView mBlogProfile;
    private CollapsingToolbarLayout collapsin;
    private GoogleMap mMap;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_public);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Publicaciones");
        mPost_key = getIntent().getExtras().getString("Blog_id");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mBlogSingleDesc = (TextView) findViewById(R.id.txtdesc);
        mBlogHorario = (TextView) findViewById(R.id.txtHorario);
        mBlogStatus = (Button) findViewById(R.id.btnstatusdesc);
        mBlogProfile = (CircularImageView) findViewById(R.id.imgProfileUserDetail);
        collapsin = (CollapsingToolbarLayout) findViewById(R.id.collapsingdetails);
        mBlogSingleImage = (ImageView) findViewById(R.id.bannerDetal);




       mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_banner = (String) dataSnapshot.child("imgbanner").getValue();
                String post_logo = (String) dataSnapshot.child("imglogo").getValue();
                String post_desc  = (String) dataSnapshot.child("desc") .getValue();
                String post_hoarios = (String) dataSnapshot.child("Horario").getValue();
                String post_status = (String) dataSnapshot.child("status").getValue();
                String post_lat = (String) dataSnapshot.child("latitud").getValue();
                String post_lon = (String) dataSnapshot.child("longitud").getValue();

                collapsin.setTitle(post_title);
                mBlogSingleDesc.setText(post_desc);
                mBlogHorario.setText(post_hoarios);
                mBlogStatus.setText(post_status);
                Picasso.with(DetailPublicActivity.this).load(post_banner).into(mBlogSingleImage);
                Picasso.with(DetailPublicActivity.this).load(post_logo).into(mBlogProfile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                // Add a marker in Sydney, Australia, and move the camera.
                LatLng sydney = new LatLng(20.0661198, -98.7631723);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Restaurant Prueba"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.setMinZoomPreference(15.0f);
                mMap.setMaxZoomPreference(15.0f);

            }
        });
        platillos();
    }


    public void platillos(){
        recyclerView = (RecyclerView) findViewById(R.id.public_platillos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
    }



    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
