package com.ahrsoft.movil.proyectofood.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahrsoft.movil.proyectofood.Consultas;
import com.ahrsoft.movil.proyectofood.R;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mPublicList;
    private DatabaseReference mDatabase;
    private long backPressedTime = 0;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    Adapter adapter;
    List<FirebaseRecyclerAdapter<Consultas, BlogViewHolder>> consultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        consultas = new ArrayList<>();


        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(myToolbar);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Publicaciones");
        mPublicList = (RecyclerView) findViewById(R.id.public_list);
        mPublicList.setLayoutManager(new LinearLayoutManager(this));
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.maincollapsing);
        CircularImageView imgProfileUser = (CircularImageView) findViewById(R.id.imgProfileUser);




        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            //String id = u();

            Profile.getCurrentProfile().getId();

            int dimensionPixelSize = getResources().getDimensionPixelSize(com.facebook.R.dimen.com_facebook_profilepictureview_preset_size_large);
            Profile profile = Profile.getCurrentProfile();
            Uri profilePictureUri = ImageRequest.getProfilePictureUri(profile.getId(), dimensionPixelSize, dimensionPixelSize);

            Picasso.with(this)
                    .load(profilePictureUri)
                    .resize(120, 120)
                    .into(imgProfileUser);

            collapsingToolbarLayout.setTitle("Hola " + name);

        } else {
            goLoginScreen();
        }


    }

    private void goLoginScreen() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {
            backPressedTime = t;
            Toast.makeText(this, "Presiona atras nuevamente para salir",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();       // bye//
        }
    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerAdapter<Consultas, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Consultas, BlogViewHolder>(
                Consultas.class,
                R.layout.card_public,
                BlogViewHolder.class,
                mDatabase

        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Consultas model, final int position) {
                final String  post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImagen(getApplicationContext(), model.getImgbanner());
                viewHolder.setLogo(getApplicationContext(), model.getImglogo());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, post_key, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this, DetailPublicActivity.class);
                        i.putExtra("Blog_id",post_key);
                        startActivity(i);
                    }
                });

            }
        };
        consultas.add(firebaseRecyclerAdapter);
        mPublicList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mView.findViewById(R.id.txttitlepublic);
            post_title.setText(title);
        }

        public void setStatus(String status) {
            Button post_status = (Button) mView.findViewById(R.id.btnstatus);
            post_status.setText(status);
        }

        public void setImagen(Context ctx, String img) {
            ImageView post_img = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(img).into(post_img);
        }

        public void setLogo(Context ctx, String img) {
            CircularImageView logo_public = (CircularImageView) mView.findViewById(R.id.imgpublic);
            Picasso.with(ctx).load(img).into(logo_public);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.itemlogout) {
            //Intent i = new Intent(this, LoginActivity.class);
            //startActivity(i);

            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            goLoginScreen();

        }

        if(id ==R.id.itemconf){
            Intent i = new Intent(this, ConfigActivity.class);
            startActivity(i);
        }

        return false;
    }
}

