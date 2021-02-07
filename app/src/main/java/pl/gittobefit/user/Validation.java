package pl.gittobefit.user;

public class Validation {
    public final static String emailValidation = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!`'@#$%^&()\\\\{}\\[\\]:;<>,.?/~_+\\-=|])(?=\\S+$).{8,}";
    public final static String passValidation = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!`'@#$%^&()\\\\{}\\[\\]:;<>,.?/~_+\\-=|])(?=\\S+$).{8,}";
}
