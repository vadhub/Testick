package com.vlg.testick.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.vlg.testick.R;
import com.vlg.testick.model.Type;
import com.vlg.testick.model.Variant;

import java.util.Arrays;

public class SaveDialog extends DialogFragment {

    public interface SaveChoose {
        void choose(boolean script, boolean file, String fileName, String scriptName);
    }

    private SaveChoose saveChoose;

    public void setSaveChoose(SaveChoose saveChoose) {
        this.saveChoose = saveChoose;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.save_choose_dialog, null);
        CheckBox isSaveScript = view.findViewById(R.id.checkboxScript);
        CheckBox isSaveFile = view.findViewById(R.id.checkboxFile);
        EditText fileName = view.findViewById(R.id.nameFile);
        EditText nameScript = view.findViewById(R.id.nameScript);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    dismiss();
                    saveChoose.choose(isSaveScript.isChecked(), isSaveFile.isChecked(), fileName.getText().toString(), nameScript.getText().toString());

                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dismiss();
                });
        return builder.create();
    }
}
