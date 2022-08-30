package net.slipp.domain;

public class Result {
    private boolean valid;

    private String errorMessage;

    private String link;

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getLink() {
        return link;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Result() {

    }

    public Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public Result(boolean valid, String errorMessage, String link) {
        this.valid = valid;
        this.errorMessage = errorMessage;
        this.link =link;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Result ok() {
        return new Result(true,null);
    }

    public static Result ok(String link) {
        return new Result(true,null,link);
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }
}
