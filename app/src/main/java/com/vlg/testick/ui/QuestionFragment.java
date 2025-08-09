package com.vlg.testick.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vlg.testick.CacheFileManager;
import com.vlg.testick.R;
import com.vlg.testick.ui.adapter.AdapterQuestion;
import com.vlg.testick.model.Quiz;
import com.vlg.testick.model.Script;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuestionFragment extends FileSaveFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EditText titleH1 = view.findViewById(R.id.titleH1);
        EditText title = view.findViewById(R.id.titleQuiz);
        Button generateButton = view.findViewById(R.id.generateQuiz);
        Button test = view.findViewById(R.id.test);
        Button editorButton = view.findViewById(R.id.editorButton);
        View bachg = view.findViewById(R.id.backg);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        Quiz quiz = app.getQuiz();

        title.setText(quiz.getTitle());
        titleH1.setText(quiz.getTitleH1());

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                quiz.setTitle(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });

        titleH1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                quiz.setTitleH1(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recyclerQuestion);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        AdapterQuestion adapterQuestion = new AdapterQuestion();
        recyclerView.setAdapter(adapterQuestion);

        adapterQuestion.setQuestions(app.getQuestions());

        FloatingActionButton button = view.findViewById(R.id.addQuestionButton);
        button.setOnClickListener(view1 -> navigation.startFragment(new VariantFragment()));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        generateButton.setOnClickListener(v -> executorService.execute(() -> {
            requireActivity().runOnUiThread(() -> {
                bachg.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            });
            quiz.setQuestions(app.getQuestions());
            quiz.setScript(new Script(quiz.getQuestions()).generate());
            String content = quiz.generate();
            String fileName = "index.html";
            boolean success = CacheFileManager.saveTextToCache(view.getContext(), fileName, content);
            if (success) {
                requireActivity().runOnUiThread(() -> Toast.makeText(view.getContext(), fileName + " Saved", Toast.LENGTH_SHORT).show());
            } else {
                requireActivity().runOnUiThread(() -> Toast.makeText(view.getContext(), "file not saved", Toast.LENGTH_SHORT).show());
            }
            requireActivity().runOnUiThread(() -> {
                bachg.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            });
        }));

        test.setOnClickListener(v -> navigation.startFragment(new WebFragment()));
        editorButton.setOnClickListener(v -> navigation.startFragment(new EditorFragment()));

    }
}
