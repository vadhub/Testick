package com.vlg.testick.model;

import java.util.List;

public class Quiz {

    private static final String DEFAULT_STYLE = ".question { margin-bottom: 20px; padding: 15px; border: 1px solid #ccc; border-radius: 5px; } button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }";

    private String title;
    private String titleH1;
    private String style = DEFAULT_STYLE;
    private String script;
    private List<Question> questions;
    public Quiz() {}

    public Quiz(String title, String titleH1, String style, String script, List<Question> questions) {
        this.title = title;
        this.titleH1 = titleH1;
        this.style = style;
        this.script = script;
        this.questions = questions;
    }

    public static class Builder {
        private String title = "small test";
        private String titleH1 = "test";
        private String style = DEFAULT_STYLE;
        private String script = "alert(\"test\")";
        private List<Question> questions = List.of(new Question.Builder().build());

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder titleH1(String titleH1) {
            this.titleH1 = titleH1;
            return this;
        }

        public Builder style(String style) {
            this.style = style;
            return this;
        }

        public Builder script(String script) {
            this.script = script;
            return this;
        }

        public Builder questions(List<Question> questions) {
            this.questions = questions;
            return this;
        }

        public Quiz build() {
            return new Quiz(title, titleH1, style, script, questions);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleH1(String titleH1) {
        this.titleH1 = titleH1;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleH1() {
        return titleH1;
    }

    public String getStyle() {
        return style;
    }

    public String getScript() {
        return script;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public String generate() {
        StringBuilder quest = new StringBuilder();
        questions.forEach(q -> quest.append(q.generate()));
        return "<html><head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <title>" + title + "</title>" +
                "    <style>" + style +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <h1>" + titleH1 + "</h1>" +
                "    <form id=\"quiz\">" + quest +
                "        <button onclick=\"checkAnswers()\" type=\"button\">Проверить ответы</button>\n" +
                "    </form>\n" +
                "    <script>\n" +
                "        function checkAnswers() {" + script + "}" +
                "    </script>" +
                "</body></html>";
    }

}
