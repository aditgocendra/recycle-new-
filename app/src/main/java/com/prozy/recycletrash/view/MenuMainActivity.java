package com.prozy.recycletrash.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prozy.recycletrash.AkunActivity;
import com.prozy.recycletrash.MainActivity;
import com.prozy.recycletrash.R;
import com.prozy.recycletrash.unggah_sesuatu.UnggahActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToogle;

    private TextView email, nama;
    private CircleImageView image;

     GoogleSignInClient mGoogleSignInClient;
    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        final Fragment menuMain = new MenuMainFragment();
        final Fragment favoriteFragment = new FavoriteFragment();
        final Fragment pesanFragment = new PesanFragment();





         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.drawer_navigasi);
        mDrawerToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToogle);
        mDrawerToogle.syncState();

        setFragment(menuMain);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        email = (TextView) headerView.findViewById(R.id.email_user);
        nama = (TextView) headerView.findViewById(R.id.nama_user);
        image = (CircleImageView) headerView.findViewById(R.id.profile_image);


        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.Home:
                        setFragment(menuMain);

                        return true;

                    case R.id.favorit:
                        setFragment(favoriteFragment);

                        return true;

                    case R.id.pesan:
                        setFragment(pesanFragment);
                        return true;

                    case R.id.akun:

                        Intent intent = new Intent(MenuMainActivity.this, AkunActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        return true;

                    default:
                        return false;

                }
            }
        });


        if  (firebaseUser.getPhotoUrl() != null){

            Glide.with(this).load(firebaseUser.getPhotoUrl().toString()).into(image);

        }
        if (firebaseUser.getDisplayName() != null)
        {
            nama.setText(firebaseUser.getDisplayName());

        }

        if (firebaseUser.getEmail() != null) {
            email.setText(firebaseUser.getEmail());
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.home){

            MenuMainFragment fragment = new MenuMainFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();

        }else if (id == R.id.Unggah){

            Intent intent = new Intent(MenuMainActivity.this, UnggahActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (id == R.id.Akun){

            Intent intent = new Intent(MenuMainActivity.this, AkunActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (id == R.id.Jual){

            Intent intent = new Intent(MenuMainActivity.this, JualActivity.class);
            intent.putExtra("kategori", "produk");
            intent.putExtra("profilnama", firebaseUser.getDisplayName());
         //   intent.putExtra("profilimage", firebaseUser.getPhotoUrl());

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }else if (id == R.id.keluar){

            firebaseAuth.signOut();
            LoginManager.getInstance().logOut();
            mGoogleSignInClient.signOut();

            Intent intent = new Intent(MenuMainActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }


        mDrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }


    private void setFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "blank");
        fragmentTransaction.commit();

    }

}
