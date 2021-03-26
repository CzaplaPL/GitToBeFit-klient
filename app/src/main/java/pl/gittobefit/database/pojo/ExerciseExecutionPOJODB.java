package pl.gittobefit.database.pojo;

import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;


public class ExerciseExecutionPOJODB
{
    private int time;
    private int series;
    private int count;
    private int exerciseId;

    public ExerciseExecutionPOJODB(ExerciseExecution exerciseExecution)
    {
        this.time = exerciseExecution.getTime();
        this.series = exerciseExecution.getSeries();
        this.count = exerciseExecution.getCount();
        this.exerciseId = exerciseExecution.getExercise().getId();
    }

    public int getTime()
    {
        return time;
    }

    public int getSeries()
    {
        return series;
    }

    public int getCount()
    {
        return count;
    }

    public int getExerciseId() {
        return exerciseId;
    }
}
