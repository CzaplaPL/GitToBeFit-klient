package pl.gittobefit.workoutforms.object;

import java.util.ArrayList;

public class ExerciseExecution
{
    int id;
    int time;
    int series;
    int count;
    Exercise exercise = new Exercise();

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
