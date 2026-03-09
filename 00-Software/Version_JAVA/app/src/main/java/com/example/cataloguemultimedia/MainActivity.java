package com.example.cataloguemultimedia;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    // Fragments conservés en mémoire
    private Fragment welcomeFragment;
    private Fragment searchFragment;
    private Fragment ztFragment;
    private Fragment wishlistFragment;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        // Gestion des insets (Edge-to-Edge)
        View contentRoot = findViewById(R.id.contentRoot);
        ViewCompat.setOnApplyWindowInsetsListener(contentRoot, (v, insets) -> {
            var sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom);
            return insets;
        });

        // Toolbar comme ActionBar
        setSupportActionBar(toolbar);

        // Hamburger + animation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Initialisation des fragments UNE SEULE FOIS
        if (savedInstanceState == null) {

            welcomeFragment = new WelcomeFragment();
            searchFragment = new SearchPageFragment();
            ztFragment = new ZtDomainFragment();
            wishlistFragment = new WishlistFragment();

            activeFragment = welcomeFragment;

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container_view, wishlistFragment, "wishlist").hide(wishlistFragment)
                    .add(R.id.fragment_container_view, ztFragment, "zt").hide(ztFragment)
                    .add(R.id.fragment_container_view, searchFragment, "search").hide(searchFragment)
                    .add(R.id.fragment_container_view, welcomeFragment, "home")
                    .commit();

            toolbar.setTitle("Accueil");
        }

        navigationView.setNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            String title = item.getTitle().toString();

            int id = item.getItemId();

            if (id == R.id.nav_search) {
                selectedFragment = searchFragment;
            } else if (id == R.id.nav_zt) {
                selectedFragment = ztFragment;
            } else if (id == R.id.nav_wishlist) {
                selectedFragment = wishlistFragment;
            } else if (id == R.id.nav_home) {
                selectedFragment = welcomeFragment;
            }

            if (selectedFragment != null && selectedFragment != activeFragment) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(activeFragment)
                        .show(selectedFragment)
                        .commit();

                activeFragment = selectedFragment;
                toolbar.setTitle(title);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openSearchWithQuery(String query) {

        getSupportFragmentManager()
                .beginTransaction()
                .hide(activeFragment)
                .show(searchFragment)
                .commit();

        activeFragment = searchFragment;
        toolbar.setTitle("Recherche");

        ((SearchPageFragment) searchFragment).performSearchFromOutside(query);
    }

    public void openSearchWithQuery(String query, String selectedContentType) {
        SearchPageFragment fragment = new SearchPageFragment();
        Bundle args = new Bundle();
        args.putString("searchContent", query);
        args.putString("selectedContentType", selectedContentType);
        fragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(null)
                .commit();
    }

}
