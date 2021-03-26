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

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
