package com.correro.alejandro.profileapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.Database;
import com.correro.alejandro.profileapp.data.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainPageActivity extends AppCompatActivity {

    @BindView(R.id.lvProfile)
    ListView lvProfile;
    @BindView(R.id.lblEmpty)
    TextView lblEmpty;
    private MainPageActivityAdapter adapter;
    private ArrayList<User> users = new ArrayList<>();
    private Database database;
    private static final int RC_PROFILE_ACTIVITY = 1;
    private static final int RC_PROFILE_UPDATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = loadUsers();
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);
        setupListView();
    }


    private void setupListView() {
        lvProfile.setEmptyView(lblEmpty);
        lvProfile.setOnItemClickListener((adapterView, view, position, id) -> editUser(adapter.getItem(position), position));
        lvProfile.setOnItemLongClickListener((adapterView, view, position, l) -> {
            deleteUser(position);
            return true;
        });
        adapter = new MainPageActivityAdapter(this, users);
        lvProfile.setAdapter(adapter);
    }

    private void deleteUser(int position) {
        User user = adapter.getItem(position);
        database.deleteUser(position);
        adapter.notifyDataSetChanged();
        Snackbar.make(lvProfile, getString(R.string.MainPageActivity_remove_user,user.getName()), Snackbar.LENGTH_LONG).setAction(getString(R.string.MainPageActivity_undo_user), view -> {
            database.insertUser(user, position);
            adapter.notifyDataSetChanged();
        }).show();
    }

    private void editUser(User user, int position) {
        ProfileActivity.startForResult(this, RC_PROFILE_UPDATE, user, position);
    }

    private ArrayList<User> loadUsers() {
        database = Database.getInstance();
        return database.getUsers();
    }

    @OnClick(R.id.lblEmpty)
    public void addNewUser() {
        ProfileActivity.startForResult(this, RC_PROFILE_ACTIVITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuAdd) {
            addNewUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_PROFILE_ACTIVITY) {
            if (data.hasExtra("user")) {
                User user = data.getParcelableExtra("user");
                database.addUser(user);
                adapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK && requestCode == RC_PROFILE_UPDATE) {
            if (data.hasExtra("user")) {
                User user = data.getParcelableExtra("user");
                database.updateUser(user, data.getIntExtra("positon", 0));
                adapter.notifyDataSetChanged();
            }
        }

    }

}
