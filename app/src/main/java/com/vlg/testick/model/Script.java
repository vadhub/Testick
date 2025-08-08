package com.vlg.testick.model;

import android.util.Log;

import java.util.List;

public class Script {
    private String initial = "let results = [];\nlet balls = 0;\nlet ball = 0;";
    private String end = "alert(\"Результаты:\\n\\n\" + results.map((r,i) => `${i+1}. ${r}`).join('\\n') + \"\\nОбщий балл: \" + balls);";
    private List<Question> questions;

    public Script(List<Question> questions) {
        this.questions = questions;
    }

    private String createBlockOneVariant(String name, String value) {
        return "          \nlet " + name + " = document.querySelector('input[name=\"" + name + "\"]:checked');\n" +
                "            results.push(" + name + " && " + name + ".value === \"" + value + "\" ? \"Верно\" : \"Неверно\");\n" +
                "            ball = " + name + " && " + name + ".value === \"" + value + "\" ? 1 : 0;\n" +
                "            balls += ball;";
    }

    private String createBlockManyVariant(String name, List<String> values) {
        StringBuilder check = new StringBuilder();
        values.forEach(c -> check.append("[...").append(name).append("].some(v => v.value === \"").append(c).append("\")").append("&&"));
        check.setLength(check.length() - 2);
        check.append(";");
        var condition = "let " + name + "Valid = " + values.size() + ".length === " + values.size() + " && \n" + check;
        return "          \nlet " + name + " = document.querySelector('input[name=\"" + name + "\"]:checked');\n" +
                condition +
                "            results.push(" + name + " && " + name + ".value === \"" + values + "\" ? \"Верно\" : \"Неверно\");\n" +
                "            ball = " + name + " && " + name + ".value === \"" + values + "\" ? 1 : 0;\n" +
                "            balls += ball;\n";
    }

    private String createBlockText(String name, String value, boolean isRightExist) {
        if (isRightExist) {
            return "          \nlet " + name + " = document.querySelector('input[name=\"" + name + "\"]').value.toLowerCase();\n" +
                    "            results.push(" + name + " && " + name + ".value === \"" + value + "\" ? \"Верно\" : \"Неверно\");\n" +
                    "            ball = " + name + " && " + name + ".value === \"" + value + "\" ? 1 : 0;\n" +
                    "            balls += ball;\n";
        }
        return "";
    }

    public String generate() {
        StringBuilder blocks = new StringBuilder();
        questions.forEach(q -> {
            Log.d("!!", q.right().size() + "");
            Log.d("!!!", q.getVariants().get(0).getName() + " " + q.right().get(0));
            switch (q.getVariants().get(0).getType()) {
                case TEXT:
                    String answer = "";
                    boolean isAnswerExist = false;
                    if (!q.right().isEmpty()) {
                        answer = q.right().get(0);
                        isAnswerExist = true;
                    }
                    blocks.append(createBlockText(q.getVariants().get(0).getName(), answer, isAnswerExist));
                    break;

                case RADIO:
                    blocks.append(createBlockOneVariant(q.getVariants().get(0).getName(), q.right().get(0)));
                    break;

                case CHECKBOX:
                    blocks.append(createBlockManyVariant(q.getVariants().get(0).getName(), q.right()));
                    break;
            }
        });
        return initial + blocks + end;
    }
}
