package com.correro.alejandro.profileapp.ui;

import android.arch.lifecycle.ViewModel;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.model.Cat;

public class ProfileActivityViewModel extends ViewModel {
    private Cat cat = null;

    public Cat getCat() {
        if (cat == null) {
            cat = new Cat(R.drawable.cat1, "Jacob");
        }
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }


}
