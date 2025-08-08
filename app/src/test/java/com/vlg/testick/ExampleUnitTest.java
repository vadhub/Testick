package com.vlg.testick;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void parserOption_test() {
        String[] examples = {
                "- \"i am\" + e",
                "- \"i am\" +",
                "- \"текст еще один\" e",
                "- \"текст\"",
                "-\"текст +e\"",
                "- \"___\"",
                "  -   \"trimmed text\"  e+  ",
                "\"trimmed text\""
        };

        for (String example : examples) {
            QuizParser.parseOption(example);
        }
    }

    @Test
    public void parseQuestion_test() {

        String[] examples = {
                "? \"iam first\" c",
                "? iam_second t",
                "  ?   \"текст с пробелами\"   r  ",
                "? текст без кавычек с пробелами c",
                "? \"кавычки в конце\"t",
                "? без кавычек но с экранированной кавычкой \"t",
                "? invalid flag x",
                "? \"unclosed c"
        };

        for (String example : examples) {
            QuizParser.parseQuestion(example);
        }
    }

    @Test
    public void parseQuiz() {
        String exampleCode = "Title title \n" +
                "H1 h1 title \n" +
                "Style default \n" +
                "? first question r \n" +
                "- Are you hui? +\n" +
                "- Are you orange? e\n" +
                "? second question c\n" +
                "- vasya +\n" +
                "- petya\n" +
                "- anton +e\n";
        QuizParser.parseQuiz(exampleCode);
    }
}