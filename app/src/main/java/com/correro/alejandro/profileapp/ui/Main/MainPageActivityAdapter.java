package com.correro.alejandro.profileapp.ui.Main;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.correro.alejandro.profileapp.R;
import com.correro.alejandro.profileapp.data.model.User;

import java.util.ArrayList;


public class MainPageActivityAdapter extends BaseAdapter {
    private final Callback mListener;
    ArrayList<User> data;
    private final LayoutInflater layoutInflater;

    interface Callback {

        void onCall(User user);

        void onSendEmail(User user);

        void onShowBrowser(User user);

        void onShowAdress(User user);

        void onDeleteUser(int position,User user);
        void onEditUser(User user, int position);
    }

    MainPageActivityAdapter(Context context, @NonNull ArrayList<User> data, Callback listener) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public User getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_main_item, parent, false);
            viewHolder = onCreateViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(position, viewHolder);
        return convertView;
    }

    public void setData(ArrayList<User> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    private ViewHolder onCreateViewHolder(View convertView) {
        ViewHolder viewHolder;
        viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    private void onBindViewHolder(int position, ViewHolder viewHolder) {
        viewHolder.bind(data.get(position), position);
    }

    public class ViewHolder {
        TextView lblName;
        TextView lblEmail;
        TextView lblPhone;
        ImageView ivAvatar;
        TextView menuPopUp;
        TextView lblEditUser;
        TextView lblDeleteUser;

        ViewHolder(View itemView) {
            lblName = itemView.findViewById(R.id.lblName);
            lblEmail = itemView.findViewById(R.id.lblEmail);
            lblPhone = itemView.findViewById(R.id.lblPhone);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            menuPopUp = itemView.findViewById(R.id.lblOptions);
            lblEditUser = itemView.findViewById(R.id.lblEditUser);
            lblDeleteUser = itemView.findViewById(R.id.lblDeleteUser);
        }

        void bind(User user, int position) {
            lblName.setText(user.getName());
            lblEmail.setText(user.getEmail());
            lblPhone.setText(user.getPhone());
            ivAvatar.setImageResource(user.getAvatar().getId());
            menuPopUp.setOnClickListener(v -> showPopup(user, v));
            lblEditUser.setOnClickListener(v -> mListener.onEditUser(user, position));
            lblDeleteUser.setOnClickListener(v -> mListener.onDeleteUser(position,user));

        }
    }

    private void showPopup(User user, View v) {

        PopupMenu popup = new PopupMenu(v.getContext(), v);
        MenuInflater menuInflater = popup.getMenuInflater();
        menuInflater.inflate(R.menu.activity_main_page_listview, popup.getMenu());
        if (user.getWeb().equals(""))
            popup.getMenu().findItem(R.id.mnuBrowser).setEnabled(false);
        else
            popup.getMenu().findItem(R.id.mnuBrowser).setEnabled(true);
        if (user.getMap().equals(""))
            popup.getMenu().findItem(R.id.mnuShowAddress).setEnabled(false);
        else
            popup.getMenu().findItem(R.id.mnuShowAddress).setEnabled(true);
        popup.setOnMenuItemClickListener(menuItem -> onMenuItemClick(user, menuItem));
        popup.show();
    }

    private boolean onMenuItemClick(User user, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.mnuCall:
                mListener.onCall(user);
                break;
            case R.id.mnuSendEmail:
                mListener.onSendEmail(user);
                break;
            case R.id.mnuShowAddress:
                mListener.onShowAdress(user);
                break;
            case R.id.mnuBrowser:
                mListener.onShowBrowser(user);
                break;
            default:
                return false;
        }
        return true;
    }
}



