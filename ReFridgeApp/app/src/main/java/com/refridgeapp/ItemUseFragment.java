package com.refridgeapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class ItemUseFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage("Did you use or throw away this item?")
                .setPositiveButton("Use", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GroceryItem item = (GroceryItem) args.getSerializable("groceryItem");

                        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());

                        GroceryItemUseDao useDao = db.groceryItemUseDao();
                        useDao.insert(new GroceryItemUse(item.getName(), new Date(), true));

                        GroceryItemDao dao = db.groceryItemDao();
                        dao.delete(item);

                        listener.onDialogClick(ItemUseFragment.this);
                    }
                })
                .setNegativeButton("Throw Away", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GroceryItem item = (GroceryItem) args.getSerializable("groceryItem");

                        AppDatabase db = AppDatabase.getInstance(getActivity().getApplicationContext());

                        GroceryItemUseDao useDao = db.groceryItemUseDao();
                        useDao.insert(new GroceryItemUse(item.getName(), new Date(), false));

                        GroceryItemDao dao = db.groceryItemDao();
                        dao.delete(item);

                        listener.onDialogClick(ItemUseFragment.this);
                    }
                });
        return builder.create();
    }

    public interface itemUseDialogListener {
        public void onDialogClick(DialogFragment dialog);
    }

    itemUseDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (itemUseDialogListener) context;
    }
}
