package com.example.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.seierfriendapp.MainActivity;
import com.example.seierfriendapp.R;

/**
 * Created by martina on 8.1.2015..
 */
public class TagIdDialogFragment extends DialogFragment {

    public static String TAGID;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_tag_id, null))
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText tagId= (EditText) TagIdDialogFragment.this.getDialog().findViewById(R.id.tagIdString);
                        TAGID=tagId.getText().toString();
                        Toast.makeText(getActivity(), "Tag ID successfully saved!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TagIdDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
