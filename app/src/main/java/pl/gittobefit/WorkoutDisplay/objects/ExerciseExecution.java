package pl.gittobefit.WorkoutDisplay.objects;

import pl.gittobefit.database.entity.training.Exercise;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.workoutforms.object.exercise.ExerciseItem;

public class ExerciseExecution
{
    private int id;
    private int time;
    private int series;
    private int count;
    private ExerciseItem exercise = new ExerciseItem();

    public ExerciseExecution(ExerciseExecutionPOJODB exerciseExecution, Exercise exercise)
    {
        this.id = exerciseExecution.getIdServer();
        this.time = exerciseExecution.getTime();
        this.series = exerciseExecution.getSeries();
        this.count = exerciseExecution.getCount();
        this.exercise = new ExerciseItem(exercise);
    }

    public int getId()
    {
        return id;
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

    public ExerciseItem getExercise()
    {
        return exercise;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setExercise(ExerciseItem exercise) {
        this.exercise = exercise;
    }
}
