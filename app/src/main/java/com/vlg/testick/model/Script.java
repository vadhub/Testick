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
        String checkedCheckbox = "const checkboxes = document.querySelectorAll('input[name=" + name + "]');\n"
                + "            const selectedValues = new Set(\n" + "    Array.from(checkboxes)\n"
                + "        .filter(cb => cb.checked)\n" + "        .map(cb => cb.value)\n" + ");\n";

        String corVal = "";
        for (String val : values) {
            corVal += "\""+val+"\",";
        }

        corVal = corVal.substring(0, corVal.length() - 1);

        String correctValues = "const correctValues = new Set([" + corVal + "]);";

        String isCorrect = "const isCorrect = \n" + "    selectedValues.size === " + values.size() + " &&\n"
                + "    [...selectedValues].every(val => correctValues.has(val)) &&\n"
                + "    [...correctValues].every(val => selectedValues.has(val));\n" + "\n" + "if (isCorrect) {\n"
                + "    results.push(\"Верно\");\n" + "    balls += 1;\n" + "} else {\n"
                + "    results.push(\"Неверно\");\n" + "} ";

        return checkedCheckbox + correctValues + isCorrect;
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
