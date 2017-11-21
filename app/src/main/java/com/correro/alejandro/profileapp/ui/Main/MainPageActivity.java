package com.correro.alejandro.profileapp.ui.Main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.model.User;
import com.correro.alejandro.profileapp.data.utils.IntentsUtils;
import com.correro.alejandro.profileapp.data.utils.ListViewUtils;
import com.correro.alejandro.profileapp.data.utils.NetworkUtils;
import com.correro.alejandro.profileapp.ui.Profile.ProfileActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainPageActivity extends AppCompatActivity implements MainPageActivityAdapter.Callback {

    @BindView(R.id.lvProfile)
    GridView lvProfile;
    @BindView(R.id.lblEmpty)
    TextView lblEmpty;
    private MainPageActivityAdapter adapter;
    private static final int RC_PROFILE_ACTIVITY = 1;
    private static final int RC_PROFILE_UPDATE = 2;
    private MainPageActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainPageActivityViewModel.class);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);
        setupListView();
    }


    private void setupListView() {
        lvProfile.setEmptyView(lblEmpty);
        adapter = new MainPageActivityAdapter(this, viewModel.getData(), this);
        lvProfile.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        lvProfile.setAdapter(adapter);
        lvProfile.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                actionMode.setTitle(getString(R.string.main_activity_number_of_number,
                        lvProfile.getCheckedItemCount(), lvProfile.getCount()));
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mnuDelete:
                        deleteUsers();
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });


    }

    private void deleteUsers() {
        List<Object> items = ListViewUtils.getSelectedItems(lvProfile, true);
        for (Object item : items) {
            viewModel.getDatabase().deleteUser((User) item);
        }
        Snackbar.make(lvProfile, getResources().getQuantityString(R.plurals.mainPageActivity_remove_users, items.size(), items.size()), Snackbar.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();

    }

    private void deleteUser(int position, List<Object> usersChecked, boolean isUserDeleteChecked) {
        User user = adapter.getItem(position);
        viewModel.getDatabase().deleteUser(position);
        adapter.notifyDataSetChanged();
        Snackbar.make(lvProfile, getString(R.string.MainPageActivity_remove_user, user.getName()), Snackbar.LENGTH_LONG).setAction(getString(R.string.MainPageActivity_undo_user), view -> {
            ListViewUtils.getSelectedItems(lvProfile, true);
            viewModel.getDatabase().insertUser(user, position);
            setCheckedUsersAfterDelete(usersChecked);
            if(isUserDeleteChecked)
                lvProfile.setItemChecked(position,true);
            adapter.notifyDataSetChanged();
        }).show();
    }

    private void editUser(User user, int position) {
        ProfileActivity.startForResult(this, RC_PROFILE_UPDATE, user, position);
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
                viewModel.getDatabase().addUser(user);
                adapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK && requestCode == RC_PROFILE_UPDATE) {
            if (data.hasExtra("user") && data.hasExtra("position")) {
                User user = data.getParcelableExtra("user");
                viewModel.getDatabase().updateUser(user, data.getIntExtra("position", 0));
                adapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onCall(User user) {
        Intent intent = IntentsUtils.newDialIntent(user.getPhone());
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.main_activity_no_dial_app), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSendEmail(User user) {
        Intent intent = IntentsUtils.newEmailIntent((user.getEmail()));
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.main_activity_no_email_app), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onShowBrowser(User user) {
        if (NetworkUtils.isConnectionAvailable(getApplicationContext())) {
            Intent intent = IntentsUtils.newViewUriIntent(Uri.parse(user.getWeb()));
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.main_activity_no_url_app), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.main_activity_no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onShowAdress(User user) {
        Intent intent = IntentsUtils.newSearchInMapIntent((user.getMap()));
        if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.main_activity_no_map_app), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDeleteUser(int position, User user) {
        boolean userDeleteIsChecked = lvProfile.isItemChecked(position);
        List<Object> usersChecked = ListViewUtils.getSelectedItems(lvProfile, true);
        deleteUser(position,usersChecked,userDeleteIsChecked);
        usersChecked.remove(user);
        setCheckedUsersAfterDelete(usersChecked);

    }

    private void setCheckedUsersAfterDelete(List<Object> usersChecked) {
        for (int i = 0; i < usersChecked.size(); i++)
            lvProfile.setItemChecked(viewModel.getDatabase().getUserPosition((User)usersChecked.get(i)), true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEditUser(User user, int position) {
        editUser(user, position);
    }
}
