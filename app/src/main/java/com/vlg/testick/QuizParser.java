package com.vlg.testick;

import com.vlg.testick.model.Question;
import com.vlg.testick.model.Quiz;
import com.vlg.testick.model.Script;
import com.vlg.testick.model.Type;
import com.vlg.testick.model.Variant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.Pair;

/**
 * <p>
 * parser language test
 * <p>
 * title token - title
 * <p>
 * titleH1 token - h1
 * <p>
 * style token - style
 * <p>
 * question token - ?
 * <p>
 * radio token - r
 * <p>
 * checkbox token - c
 * <p>
 * text token - t
 * <p>
 * end line token - e
 * <p>
 * right answer token - +
 * <p><u>example:</u></p>
 * <p>
 * title title
 * h1 titleH1
 * style default
 * <p>
 * ? who r
 * <p>- "dr. who"
 * <p>- "i am" + e
 * <p>
 * ? "iam second" c
 * <p>- "fffff"
 * <p>- "ffff" +
 * <p>- "ffffss" e
 * <p>
 * ? third
 * <p>___ "who is fuck"
 **/

public class QuizParser {

    public static class UniqueNameGenerator {
        private static long lastTimestamp = 0;
        private static int counter = 0;

        public static synchronized String generate() {
            long current = System.currentTimeMillis();
            if (current == lastTimestamp) {
                counter++;
            } else {
                counter = 0;
                lastTimestamp = current;
            }
            return "el-" + current + "-" + counter;
        }

        public static synchronized String generate(String type) {
            long current = System.currentTimeMillis();
            if (current == lastTimestamp) {
                counter++;
            } else {
                counter = 0;
                lastTimestamp = current;
            }
            return type + "-" + current + "-" + counter;
        }
    }

    private static final Pattern OPTION_PATTERN = Pattern.compile("^\\s*-\\s*(?:\"([^\"]*)\"|([^\"]+?))\\s*([e+\\s]*)\\s*");
    private static final Pattern QUESTION_PATTERN = Pattern.compile("^\\s*\\?\\s*(?:\"([^\"]*)\"|([^\"]+?))\\s*([ctr])\\s*");

    public static Quiz parseQuiz(String code) {
        String[] lines = code.split("\n");

        Quiz quiz = new Quiz();
        Question current = null;
        Variant currentV;
        List<Variant> variants = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        int i = 0;
        Type type = Type.TEXT;
        String name = "";

        for (String line : lines) {
            if (!line.startsWith("-") && !line.startsWith("?") && !line.startsWith("___")) {
                System.out.println("-------- Parse metadata --------");
                parseMetadata(line, quiz);
            } else if (line.startsWith("?")) {
                if (current != null && !variants.isEmpty()) {
                    current.setVariants(variants);
                    variants = new ArrayList<>();
                    questions.add(current);
                }
                Pair<String, String> p = parseQuestion(line);
                type = detectTypeFromFlag(p.getFirst());
                name = p.getFirst() + i;
                current = new Question();
                current.setTitle(p.getSecond());
                i++;
            } else if (line.startsWith("-")) {
                currentV = parseOption(line, name, type);
                variants.add(currentV);
            } else if (line.startsWith("___")) {
                currentV = parseTextAnswer(line, name);
                variants.add(currentV);
            }
        }

        if (current != null) {
            current.setVariants(variants);
            questions.add(current);
        }

        questions.forEach(q -> System.out.println(Arrays.toString(q.getVariants().toArray())));
        quiz.setQuestions(questions);
        quiz.setScript(new Script(questions).generate());
        return quiz;
    }

    public static void parseMetadata(String line, Quiz quiz) {
        String[] strings = line.split(" ", 2);
        if (strings.length < 2) {
            System.err.println("Invalid text answer: " + line);
            return;
        }
        switch (strings[0]) {
            case "Style":
                if (!strings[1].trim().equals("default")) quiz.setStyle(strings[1]);
                break;

            case "Title":
                quiz.setTitle(strings[1]);
                break;

            case "H1":
                quiz.setTitleH1(strings[1]);
                break;
        }
    }

    public static Pair<String, String> parseQuestion(String line) {
        Pair<String, String> pair;
        Matcher matcher = QUESTION_PATTERN.matcher(line);
        if (matcher.matches()) {
            String text = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            String flag = matcher.group(3);
            pair = new Pair<>(flag, text);
        } else {
            pair = new Pair<>("!", "!");
            System.out.println("Invalid format: " + line);
        }

        return pair;
    }

    public static Variant parseOption(String line, String name, Type type) {
        Variant variant;
        Matcher matcher = OPTION_PATTERN.matcher(line);
        if (matcher.matches()) {
            String text = matcher.group(1) != null ?
                    matcher.group(1) :
                    matcher.group(2);
            String flags = matcher.group(3) != null ?
                    matcher.group(3) : "";

            boolean isRight = flags.contains("+");
            boolean hasNewLine = flags.contains("e");
            variant = new Variant.Builder().name(name).value(UniqueNameGenerator.generate()).type(type).text(text).isRight(isRight).addNewLine(!hasNewLine).build();

        } else {
            variant = new Variant.Builder().build();
            System.out.println("Invalid format: " + line);
        }

        return variant;
    }

    public static Variant parseTextAnswer(String line, String name) {
        Variant variantos;
        String[] variant = line.split(" ", 2);
        if (variant.length < 2) {
            System.err.println("Invalid text answer: " + line);
            return null;
        }
        String text = variant[1];
        text = text.replaceAll("^\"|\"$", "");

        variantos = new Variant.Builder().name(name).type(Type.TEXT).text(text).addNewLine().build();
        System.out.println("Text: " + text);
        return variantos;
    }

    public static Type detectTypeFromFlag(String flag) {
        switch (flag) {
            case "r":
                return Type.RADIO;
            case "c":
                return Type.CHECKBOX;
            case "t":
                return Type.TEXT;
            default:
                throw new IllegalArgumentException("Unknown flag: " + flag);
        }
    }
}
