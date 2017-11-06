package com.correro.alejandro.profileapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.model.Cat;
import com.correro.alejandro.profileapp.data.model.User;
import com.correro.alejandro.profileapp.data.utils.IntentsUtils;
import com.correro.alejandro.profileapp.data.utils.NetworkUtils;
import com.correro.alejandro.profileapp.data.utils.ValidationUtils;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.ivCat)
    ImageView ivCat;
    @BindView(R.id.txtWeb)
    EditText txtWeb;
    @BindView(R.id.ivWeb)
    ImageView ivWeb;
    @BindView(R.id.lblWeb)
    TextView lblWeb;
    @BindView(R.id.txtAddress)
    EditText txtAddress;
    @BindView(R.id.ivAddress)
    ImageView ivAddress;
    @BindView(R.id.lblAddress)
    TextView lblAddress;
    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.ivEmail)
    ImageView ivEmail;
    @BindView(R.id.txtPhone)
    EditText txtPhone;
    @BindView(R.id.ivPhone)
    ImageView ivPhone;
    @BindView(R.id.lblEmail)
    TextView lblEmail;
    @BindView(R.id.lblPhone)
    TextView lblPhone;
    @BindView(R.id.txtName)
    EditText txtName;
    @BindView(R.id.lblName)
    TextView lblName;
    @BindView(R.id.lblCatName)
    TextView lblCatName;
    @BindColor(R.color.focus)
    int focusColor;
    private static final String STATE_CAT = "STATE_CAT";
    final int RC_CAT = 1;
    //Value of the actual ivCat drawable
    int drawableId;
    private static final String EXTRA_USER = "EXTRA_USER";
    private static final String EXTRA_POSITION = "EXTRA_POSITION";
    private User user = null;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainIntentData(getIntent());
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        startValues();
        ivCat.setTag(R.drawable.cat1);
        if (user != null)
            setDate(user);
        drawableId = (Integer) ivCat.getTag();

    }

    private void setDate(User user) {
        txtName.setText(user.getName());
        txtEmail.setText(user.getEmail());
        txtAddress.setText(user.getMap());
        txtPhone.setText(user.getPhone());
        txtWeb.setText(user.getWeb());
        ivCat.setImageResource(user.getAvatar());
        ivCat.setTag(user.getAvatar());


    }

    private void startValues() {
        txtName.requestFocus();
        ivWeb.setEnabled(false);
        ivPhone.setEnabled(false);
        ivEmail.setEnabled(false);
        ivAddress.setEnabled(false);
    }

    private void obtainIntentData(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_USER) && intent.hasExtra(EXTRA_POSITION)) {
            user = intent.getParcelableExtra(EXTRA_USER);
            position = intent.getIntExtra(EXTRA_POSITION, 0);
        }
    }

    @OnFocusChange(R.id.txtName)
    public void focusName(boolean focus) {
        if (focus) {
            lblName.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lblName.setTypeface(Typeface.DEFAULT);
        }


    }

    @OnFocusChange(R.id.txtEmail)
    public void focusEmail(boolean focus) {
        if (focus) {
            lblEmail.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lblEmail.setTypeface(Typeface.DEFAULT);
        }


    }

    @OnFocusChange(R.id.txtPhone)
    public void focusPhone(boolean focus) {
        if (focus) {
            lblPhone.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lblPhone.setTypeface(Typeface.DEFAULT);
        }


    }

    @OnFocusChange(R.id.txtAddress)
    public void focusAddress(boolean focus) {
        if (focus) {
            lblAddress.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lblAddress.setTypeface(Typeface.DEFAULT);
        }


    }

    @OnFocusChange(R.id.txtWeb)
    public void focusWeb(boolean focus) {
        if (focus) {
            lblWeb.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lblWeb.setTypeface(Typeface.DEFAULT);
        }


    }

    @OnTextChanged(value = R.id.txtEmail, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void emailIconChange() {
        if (ValidationUtils.isValidEmail(txtEmail.getText().toString())) {
            ivEmail.setImageResource(R.drawable.ic_email_black_24dp);
            ivEmail.setEnabled(true);
        } else {
            ivEmail.setImageResource(R.drawable.ic_email_grey_24dp);
            ivEmail.setEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.txtPhone, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void phoneIconChange() {
        if (ValidationUtils.isValidPhone(txtPhone.getText().toString())) {
            ivPhone.setImageResource(R.drawable.ic_local_phone_black_24dp);
            ivPhone.setEnabled(true);
        } else {
            ivPhone.setImageResource(R.drawable.ic_local_phone_grey_24dp);
            ivPhone.setEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.txtWeb, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void webIconChange() {
        if (ValidationUtils.isValidUrl(txtWeb.getText().toString())) {
            ivWeb.setImageResource(R.drawable.ic_web_black_24dp);
            ivWeb.setEnabled(true);
        } else {
            ivWeb.setImageResource(R.drawable.ic_web_grey_24dp);
            ivWeb.setEnabled(false);
        }
    }

    @OnTextChanged(value = R.id.txtAddress, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void addressIconChange() {
        if (!TextUtils.isEmpty(txtAddress.getText().toString())) {
            ivAddress.setImageResource(R.drawable.ic_map_black_24dp);
            ivAddress.setEnabled(true);
        } else {
            ivAddress.setImageResource(R.drawable.ic_map_grey_24dp);
            ivAddress.setEnabled(false);
        }
    }

    @OnClick(R.id.ivEmail)
    public void emailSend() {
        if (ivEmail.isEnabled()) {
            Intent intent = IntentsUtils.newEmailIntent((txtEmail.getText().toString()));
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.main_activity_no_email_app), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.main_activity_no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.ivPhone)
    public void phoneDial() {
        if (ivPhone.isEnabled()) {
            Intent intent = IntentsUtils.newDialIntent(txtPhone.getText().toString());
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.main_activity_no_dial_app), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @OnClick(R.id.ivAddress)
    public void addressSearch() {
        if (ivAddress.isEnabled()) {
            Intent intent = IntentsUtils.newSearchInMapIntent((txtAddress.getText().toString()));
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.main_activity_no_map_app), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @OnClick(R.id.ivWeb)
    public void navigateUrl() {
        if (ivWeb.isEnabled() && NetworkUtils.isConnectionAvailable(getApplicationContext())) {
            Intent intent = IntentsUtils.newViewUriIntent(Uri.parse(txtWeb.getText().toString()));
            if (IntentsUtils.isActivityAvailable(getApplicationContext(), intent)) {
                startActivity(intent);
            } else {
                Toast.makeText(this, getString(R.string.main_activity_no_url_app), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.main_activity_no_internet), Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick({R.id.ivCat, R.id.lblCatName})
    public void clickCat() {
        Intent intent = new Intent(this, CatSelectionActivity.class);
        intent.putExtra("cat", (int) ivCat.getTag());
        startActivityForResult(intent, RC_CAT);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_CAT, new Cat(drawableId, lblCatName.getText().toString()));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Cat cat = savedInstanceState.getParcelable(STATE_CAT);
        assert cat != null;
        ivCat.setImageResource(cat.getId());
        ivCat.setTag(cat.getId());
        drawableId = cat.getId();
        lblCatName.setText(cat.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RC_CAT) {
            if (data.hasExtra("cat")) {
                Cat cat = data.getParcelableExtra("cat");
                ivCat.setImageResource(cat.getId());
                drawableId = cat.getId();
                ivCat.setTag(drawableId);
                lblCatName.setText(cat.getName());
            }
        }

    }

    public static void startForResult(Activity context, int requestCode) {
        context.startActivityForResult(new Intent(context, ProfileActivity.class), requestCode);
    }

    public static void startForResult(Activity context, int requestCode, User user, int position) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_USER, user);
        intent.putExtra(EXTRA_POSITION, position);
        context.startActivityForResult(intent, requestCode);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_profile, menu);
        if (user != null)
            menu.findItem(R.id.mnuAccept).setTitle(R.string.menu_add_title);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuAccept) {
            //I consider that the only essential information are emaiil,phone and name.
            if (ivEmail.isEnabled() && ivPhone.isEnabled() && !TextUtils.isEmpty(txtName.getText().toString())) {
                addStudent();
                return true;
            } else
                Toast.makeText(this, getString(R.string.profile_activity_option_fail), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addStudent() {
        Intent result = new Intent();
        result.putExtra("user", new User(txtName.getText().toString(), txtPhone.getText().toString(), txtEmail.getText().toString(), (int) ivCat.getTag(), txtWeb.getText().toString(), txtAddress.getText().toString()));
        result.putExtra("position", position);
        setResult(RESULT_OK, result);
        finish();
    }

}
