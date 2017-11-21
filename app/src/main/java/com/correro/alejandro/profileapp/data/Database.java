package com.correro.alejandro.profileapp.data;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.model.Cat;
import com.correro.alejandro.profileapp.data.model.User;

import java.util.ArrayList;

public class Database {

    private static Database instance;

    private ArrayList<User> users;

    private Database() {

        users = new ArrayList<User>();

// Users for tests

      /*  users.add(new User("alex1","666666666","alex@hotmail.com", new Cat(R.drawable.cat1,"Jacob"),"https://hotmail.com","casa alex"));
        users.add(new User("alex2","666666666","alex@hotmail.com", new Cat(R.drawable.cat1,"Jacob"),"https://hotmail.com","casa alex"));
        users.add(new User("alex3","666666666","alex@hotmail.com", new Cat(R.drawable.cat1,"Jacob"),"https://hotmail.com","casa alex"));
        users.add(new User("alex4","666666666","alex@hotmail.com", new Cat(R.drawable.cat1,"Jacob"),"https://hotmail.com","casa alex"));
        users.add(new User("alex5","666666666","alex@hotmail.com", new Cat(R.drawable.cat1,"Jacob"),"https://hotmail.com","casa alex"));
        users.add(new User("alex6","666666666","alex@hotmail.com", new Cat(R.drawable.cat1,"Jacob"),"https://hotmail.com","casa alex"));
   */
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void updateUser(User user, int position) {
        users.set(position, user);
    }

    public void deleteUser(int position) {
        users.remove(position);
    }

    public void insertUser(User user, int position) {
        users.add(position, user);
    }

    public void deleteUser(User user) {
        users.remove(user);
    }

    public int getUserPosition(User user){
        return users.indexOf(user);
    }
}
