package com.example.andrej.seabattle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.Toast;


public class PauseDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder
                .setView(inflater.inflate(R.layout.fragment_pause_dialog, null))
                .setPositiveButton("exit game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(getContext(), "exit game", Toast.LENGTH_SHORT).show();
                        dismiss();
                        Intent startGameIntent = new Intent(getContext(), MainActivity.class);
                        startActivity(startGameIntent);
                    }
                })
                .setNegativeButton("Resume Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });
        return builder.create();
        //return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof GameActivity){
            ((GameActivity) activity).onResume();
        }
    }
}
