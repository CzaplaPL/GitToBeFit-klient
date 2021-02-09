package pl.gittobefit.user;

public interface Validation {
    String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!`'@#$%^&()\\\\{}\\[\\]:;<>,?/~_+\\-=|])(?=\\S+$).{8,}";
}
