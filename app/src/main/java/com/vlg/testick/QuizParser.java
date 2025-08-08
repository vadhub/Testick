package com.vlg.testick;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern OPTION_PATTERN = Pattern.compile("^\\s*-\\s*(?:\"([^\"]*)\"|([^\"\\s]+(?:[^\"\\S]+[^\"\\s]+)*))\\s*([+e]*)\\s*$");
    private static final Pattern QUESTION_PATTERN = Pattern.compile("^\\s*\\?\\s*(?:\"([^\"]*)\"|([^\"]+?))\\s*([ctr])\\s*");

    public static void parseQuiz(String code) {
        String[] lines = code.split("\n");

        for (String line : lines) {
            if (!line.startsWith("-") && !line.startsWith("?") && !line.startsWith("___")) {
                System.out.println("-------- Parse metadata --------");
                parseMetadata(line);
            } else if (!line.startsWith("-") && line.startsWith("?") && !line.startsWith("___")) {
                System.out.println("-------- Parse question --------");
                parseQuestion(line);
            } else if (line.startsWith("-") && !line.startsWith("?") && !line.startsWith("___")) {
                System.out.println("-------- Parse variant --------");
                parseOption(line);
            } else if (!line.startsWith("-") && !line.startsWith("?") && line.startsWith("___")) {
                System.out.println("-------- Parse variant with text --------");
                String[] variant = line.split(" ");
                String text = variant[1];
                if (text.startsWith("\"")) {
                    text = text.substring(1);
                }
                if (text.endsWith("\"")) {
                    text = text.substring(text.length() - 1);
                }
                System.out.println("Text: " + text);
            }
        }
    }

    public static void parseMetadata(String line) {
        String[] strings = line.split(" ", 2);
        String style = "";
        String title = "";
        String h1 = "";
        switch (strings[0]) {
            case "Style":
                style = strings[1];
                break;

            case "Title":
                title = strings[1];
                break;

            case "H1":
                h1 = strings[1];
                break;
        }

        System.out.println("Style: " + style);
        System.out.println("Title: " + title);
        System.out.println("H1: " + h1);
    }

    public static void parseQuestion(String line) {
        Matcher matcher = QUESTION_PATTERN.matcher(line);
        if (matcher.matches()) {
            String text = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            String flag = matcher.group(3);

            System.out.println("Text: " + text);
            System.out.println("Flag: " + flag);
            System.out.println("------");
        } else {
            System.out.println("Invalid format: " + line);
        }
    }

    public static void parseOption(String line) {
        Matcher matcher = OPTION_PATTERN.matcher(line);
        if (matcher.matches()) {
            String text = matcher.group(1) != null ?
                    matcher.group(1) :
                    matcher.group(2);
            String flags = matcher.group(3) != null ?
                    matcher.group(3) : "";

            boolean isRight = flags.contains("+");
            boolean hasNewLine = flags.contains("e");

            System.out.println("Text: " + text);
            System.out.println("Is right: " + isRight);
            System.out.println("New line: " + hasNewLine);
            System.out.println("------");
        } else {
            System.out.println("Invalid format: " + line);
        }
    }
}
