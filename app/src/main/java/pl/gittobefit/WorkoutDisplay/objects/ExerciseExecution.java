package pl.gittobefit.WorkoutDisplay.objects;

import pl.gittobefit.database.entity.training.Exercise;

public class ExerciseExecution
{
    private int id;
    private int time;
    private int series;
    private int count;
    private Exercise exercise = new Exercise();

    public int getId() {
        return id;
    }

    public int getTime() {
        return time;
    }

    public int getSeries() {
        return series;
    }

    public int getCount() {
        return count;
    }

    public Exercise getExercise() {
        return exercise;
    }
}
