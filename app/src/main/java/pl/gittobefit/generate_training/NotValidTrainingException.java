package pl.gittobefit.generate_training;

public class NotValidTrainingException extends RuntimeException {
    public NotValidTrainingException(String message) {
        super(message);
    }
}
