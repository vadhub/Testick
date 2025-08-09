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
import java.util.UUID;

public class VariantDialog extends DialogFragment {

    public interface VariantCreate {
        void create(Variant variant);
    }

    private VariantCreate variantCreate;

    public void setVariantCreate(VariantCreate variantCreate) {
        this.variantCreate = variantCreate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.variant, null);
        EditText name = view.findViewById(R.id.name);
        EditText editQuestion = view.findViewById(R.id.value);
        EditText editAnswerText = view.findViewById(R.id.textET);
        CheckBox isRightCheckbox = view.findViewById(R.id.isRight);

        final Type[] typet = {Type.RADIO};
        Spinner typeSpinner = view.findViewById(R.id.type);
        String[] type = Arrays.stream(Type.values()).map(Type::getValue).toArray(String[]::new);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, type);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(spinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                if (item.equals(Type.TEXT.getValue())) {
                    typet[0] = Type.TEXT;
                    isRightCheckbox.setVisibility(View.GONE);
                } else if (item.equals(Type.RADIO.getValue())) {
                    typet[0] = Type.RADIO;
                    isRightCheckbox.setVisibility(View.VISIBLE);
                } else if (item.equals(Type.CHECKBOX.getValue())) {
                    typet[0] = Type.CHECKBOX;
                    isRightCheckbox.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    dismiss();

                    Variant variant = new Variant.Builder()
                            .name(name.getText().toString())
                            .isRight(isRightCheckbox.isChecked())
                            .type(typet[0])
                            .value(editQuestion.getText().toString())
                            .text(editAnswerText.getText().toString())
                            .addNewLine()
                            .build();

                    variantCreate.create(variant);

                }).setNegativeButton("Cancel", (dialogInterface, i) -> {
                    dismiss();
                });
        return builder.create();
    }
}
