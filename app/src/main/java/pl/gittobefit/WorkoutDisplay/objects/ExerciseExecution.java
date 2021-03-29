package pl.gittobefit.WorkoutDisplay.objects;

import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;

public class ExerciseExecution
{
    private int id;
    private int time;
    private int series;
    private int count;
    private Exercise exercise = new Exercise();

    public ExerciseExecution(ExerciseExecutionPOJODB exerciseExecution, Exercise exercise)
    {
        this.id=exerciseExecution.getIdServer();
        this.time=exerciseExecution.getTime();
        this.series = exerciseExecution.getSeries();
        this.count = exerciseExecution.getCount();
        this.exercise= exercise;
    }

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
