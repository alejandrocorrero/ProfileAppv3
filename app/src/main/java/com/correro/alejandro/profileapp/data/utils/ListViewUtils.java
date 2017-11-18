package com.correro.alejandro.profileapp.data.utils;


import android.util.SparseBooleanArray;
import android.widget.GridView;
import android.widget.ListView;

import com.correro.alejandro.profileapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListViewUtils {

    private ListViewUtils() {
    }

    public static List<Object> getSelectedItems(GridView listView, boolean
            uncheck) {
        ArrayList<Object> result = new ArrayList<>();
        SparseBooleanArray selec = listView.getCheckedItemPositions();
        for (int i = 0; i < selec.size(); i++) {
            // If selected item.
            if (selec.valueAt(i)) {
                int position = selec.keyAt(i);
                // Uncheck item.
                if (uncheck) {
                    listView.setItemChecked(position, false);
                }
                result.add(listView.getItemAtPosition(selec.keyAt(i)));
            }
        }
        return result;
    }

}