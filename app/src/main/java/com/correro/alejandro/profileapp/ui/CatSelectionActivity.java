package com.correro.alejandro.profileapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.model.Cat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CatSelectionActivity extends AppCompatActivity {

    @BindView(R.id.ivCat1)
    ImageView ivCat1;
    @BindView(R.id.ivCat2)
    ImageView ivCat2;
    @BindView(R.id.ivCat3)
    ImageView ivCat3;
    @BindView(R.id.ivCat5)
    ImageView ivCat5;
    @BindView(R.id.ivCat4)
    ImageView ivCat4;
    @BindView(R.id.ivCat6)
    ImageView ivCat6;
    @BindView(R.id.lblCat1)
    TextView lblCat1;
    @BindView(R.id.lblCat2)
    TextView lblCat2;
    @BindView(R.id.lblCat3)
    TextView lblCat3;
    @BindView(R.id.lblCat4)
    TextView lblCat4;
    @BindView(R.id.lblCat5)
    TextView lblCat5;
    @BindView(R.id.lblCat6)
    TextView lblCat6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_select);
        ButterKnife.bind(this);
        onCreateValues();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            actualCat(extras);
        }

    }

    private void actualCat(Bundle extras) {
        int cat = extras.getInt("cat");
        if (cat == ((Cat) ivCat1.getTag()).getId())
            ivCat1.setAlpha((float) 0.5);
        if (cat == ((Cat) ivCat2.getTag()).getId())
            ivCat2.setAlpha((float) 0.5);
        if (cat == ((Cat) ivCat3.getTag()).getId())
            ivCat3.setAlpha((float) 0.5);
        if (cat == ((Cat) ivCat4.getTag()).getId())
            ivCat4.setAlpha((float) 0.5);
        if (cat == ((Cat) ivCat5.getTag()).getId())
            ivCat5.setAlpha((float) 0.5);
        if (cat == ((Cat) ivCat6.getTag()).getId())
            ivCat6.setAlpha((float) 0.5);
    }

    private void onCreateValues() {
        ivCat1.setTag(new Cat(R.drawable.cat1, lblCat1.getText().toString()));
        ivCat2.setTag(new Cat(R.drawable.cat2, lblCat2.getText().toString()));
        ivCat3.setTag(new Cat(R.drawable.cat3, lblCat3.getText().toString()));
        ivCat4.setTag(new Cat(R.drawable.cat4, lblCat4.getText().toString()));
        ivCat5.setTag(new Cat(R.drawable.cat5, lblCat5.getText().toString()));
        ivCat6.setTag(new Cat(R.drawable.cat6, lblCat6.getText().toString()));
        lblCat1.setTag(new Cat(R.drawable.cat1, lblCat1.getText().toString()));
        lblCat2.setTag(new Cat(R.drawable.cat2, lblCat2.getText().toString()));
        lblCat3.setTag(new Cat(R.drawable.cat3, lblCat3.getText().toString()));
        lblCat4.setTag(new Cat(R.drawable.cat4, lblCat4.getText().toString()));
        lblCat5.setTag(new Cat(R.drawable.cat5, lblCat5.getText().toString()));
        lblCat6.setTag(new Cat(R.drawable.cat6, lblCat6.getText().toString()));
    }


    private void intentForCat(Cat value) {
        Intent result = new Intent();
        result.putExtra("cat", value);
        setResult(RESULT_OK, result);
        finish();
    }

    @OnClick({R.id.ivCat1, R.id.ivCat2, R.id.ivCat3, R.id.ivCat4, R.id.ivCat5, R.id.ivCat6, R.id.lblCat1, R.id.lblCat2, R.id.lblCat3, R.id.lblCat4, R.id.lblCat5, R.id.lblCat6})
    public void catCLick(View view) {
        intentForCat((Cat) view.getTag());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
