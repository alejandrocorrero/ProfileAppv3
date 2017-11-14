package com.correro.alejandro.profileapp.ui;

import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.Database;
import com.correro.alejandro.profileapp.data.model.Cat;
import com.correro.alejandro.profileapp.data.model.User;

import java.util.ArrayList;

public class MainPageActivityViewModel extends ViewModel {
    private ArrayList<User> data = null;
    private Database database = null;

    public Database getDatabase() {
        if (database == null) {
            database = Database.getInstance();
        }
        return database;
    }


    public ArrayList<User> getData() {
        if (data == null) {
            database=Database.getInstance();
            data = database.getUsers();
        }
        return data;
    }


}

