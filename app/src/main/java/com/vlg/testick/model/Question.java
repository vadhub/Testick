package com.vlg.testick.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Question {
    private String title;
    private List<Variant> variants;
    private String styleClass = ".question";

    public Question(String title, List<Variant> variants, String styleClass) {
        this.title = title;
        this.variants = variants;
        this.styleClass = styleClass;
    }

    public Question() {}

    public static class Builder {
        private String title = "";
        private List<Variant> variants = List.of(new Variant.Builder().build());
        private String styleClass = ".question";

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder variants(List<Variant> variants) {
            this.variants = variants;
            return this;
        }

        public Builder styleClass(String styleClass) {
            this.styleClass = styleClass;
            return this;
        }

        public Question build() {
            return new Question(title, variants, styleClass);
        }
    }

    public String generate() {
        StringBuilder variantos = new StringBuilder();
        variants.forEach(v -> variantos.append(v.generate()));
        return "<div class=\""+styleClass+"\">"+"<h3>"+title+"</h3>"+variantos+"</div>";
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public List<String> right() {
        return variants.stream().filter(Variant::isRight).map(Variant::getValue).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return title + " " + styleClass;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public String getTitle() {
        return title;
    }

    public String getStyleClass() {
        return styleClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(title, question.title) && Objects.equals(variants, question.variants) && Objects.equals(styleClass, question.styleClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, variants, styleClass);
    }
}
