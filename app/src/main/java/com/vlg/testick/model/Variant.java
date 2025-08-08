package com.vlg.testick.model;

import java.util.Objects;

public class Variant {
    private String name;
    private Type type;
    private String value;
    private String text;
    private boolean isNewLine;
    private boolean isRight;

    public Variant(String name, Type type, String value, String text, boolean isNewLine, boolean isRight) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.text = text;
        this.isNewLine = isNewLine;
        this.isRight = isRight;
    }

    public static class Builder {
        private String name = "q1";
        private Type type = Type.RADIO;
        private String value = "hello";
        private String text = "Hello";
        private boolean isNewLine;
        private boolean isRight;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder addNewLine() {
            isNewLine = true;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder isRight() {
            isRight = true;
            return this;
        }

        public Builder isRight(boolean isRight) {
            this.isRight = isRight;
            return this;
        }

        public Builder addNewLine(boolean isNewLine) {
            this.isNewLine = isNewLine;
            return this;
        }

        public Variant build() {
            return new Variant(name, type, value, text, isNewLine, isRight);
        }
    }

    /**
     * if Type is text then text is placeholder
     * value is answer
     * @return String
     */
    public String generate() {
        if (type == Type.TEXT) {
            return "<label><input name=\"" + name + "\" placeholder=\""+text+"\" type=\"text\" /></label>" + (isNewLine ? "<br />" : "");
        }
        return "<label><input name=\"" + name + "\" type=\"" + type.getValue() + "\" value=\"" + value + "\"/>" + text + "</label>" + (isNewLine ? "<br />" : "");
    }

    public String getName() {
        return name;
    }

    public boolean isRight() {
        return isRight;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public boolean isNewLine() {
        return isNewLine;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNewLine(boolean newLine) {
        isNewLine = newLine;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    @Override
    public String toString() {
        return name + " " +type + " " + value + " " + text + " isNewLine: " + isNewLine + " isRight: " + isRight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variant variant = (Variant) o;
        return isNewLine == variant.isNewLine && isRight == variant.isRight && Objects.equals(name, variant.name) && type == variant.type && Objects.equals(value, variant.value) && Objects.equals(text, variant.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, value, text, isNewLine, isRight);
    }
}
