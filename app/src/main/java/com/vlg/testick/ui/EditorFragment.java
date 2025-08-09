package com.vlg.testick.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vlg.testick.CacheFileManager;
import com.vlg.testick.QuizParser;
import com.vlg.testick.R;
import com.vlg.testick.model.Quiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class EditorFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.editor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button generate = view.findViewById(R.id.generateQuizFromText);
        Button test = view.findViewById(R.id.testWebButton);
        Button help = view.findViewById(R.id.help);
        EditText code = view.findViewById(R.id.editor);
        View bachg = view.findViewById(R.id.backgr);
        ProgressBar progressBar = view.findViewById(R.id.progressBarEditor);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        TextView console = view.findViewById(R.id.console);
        AtomicReference<String> consoleText = new AtomicReference<>("");
        generate.setOnClickListener(v -> executorService.execute(() -> {
            requireActivity().runOnUiThread(() -> {
                bachg.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            });
            try {
                consoleText.set("start");
                Quiz quiz = QuizParser.parseQuiz(code.getText().toString());
                String content = quiz.generate();
                String fileName = "index.html";
                boolean success = CacheFileManager.saveTextToCache(view.getContext(), fileName, content);
                if (success) {
                    requireActivity().runOnUiThread(() -> Toast.makeText(view.getContext(), fileName + " Saved", Toast.LENGTH_SHORT).show());
                } else {
                    requireActivity().runOnUiThread(() -> Toast.makeText(view.getContext(), "file not saved", Toast.LENGTH_SHORT).show());
                }
                consoleText.set("done");
            } catch (Exception e) {
                Log.d("!!!", e.getMessage());
                requireActivity().runOnUiThread(() -> Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
                consoleText.set(e.getMessage());
            }

            requireActivity().runOnUiThread(() -> {
                bachg.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
            });
        }));
        console.setVisibility(View.VISIBLE);
        console.setText(consoleText.get());
        test.setOnClickListener(v -> navigation.startFragment(new WebFragment()));
        help.setOnClickListener(v -> navigation.startFragment(new HelpFragment()));
    }
}
