package com.vlg.testick.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vlg.testick.R;
import com.vlg.testick.model.Question;
import com.vlg.testick.model.Variant;
import com.vlg.testick.ui.adapter.AdapterVariant;

import java.util.ArrayList;
import java.util.List;

public class VariantFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.variant_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button createQuestion = view.findViewById(R.id.createQuestion);
        EditText title = view.findViewById(R.id.titleQuiz);
        List<Variant> variants = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerVariant);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        AdapterVariant adapterVariant = new AdapterVariant();
        recyclerView.setAdapter(adapterVariant);

        adapterVariant.setVariants(variants);

        FloatingActionButton button = view.findViewById(R.id.addVariantButton);
        button.setOnClickListener(view1 -> {
            VariantDialog variantDialog = new VariantDialog();
            variantDialog.setVariantCreate(v -> {
                variants.add(v);
                adapterVariant.setVariants(variants);
            });
            variantDialog.show(getParentFragmentManager(), "VariantDialog");
        });

        createQuestion.setOnClickListener(view2 -> {
            Question question = new Question.Builder().variants(variants).title(title.getText().toString()).build();
            app.getQuestions().add(question);
            navigation.startFragment(new QuestionFragment());
        });
    }
}
