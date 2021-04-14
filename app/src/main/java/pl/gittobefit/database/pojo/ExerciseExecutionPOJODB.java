package pl.gittobefit.database.pojo;

import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;


public class ExerciseExecutionPOJODB
{
    private int idServer;
    private int idServerPlanList;
    private int idServerTraining;
    private int time;
    private int series;
    private int count;
    private int exerciseId;

    public ExerciseExecutionPOJODB(ExerciseExecution exerciseExecution,int idPlanList,int idTraining)
    {
        this.time = exerciseExecution.getTime();
        this.series = exerciseExecution.getSeries();
        this.count = exerciseExecution.getCount();
        this.exerciseId = exerciseExecution.getExercise().getId();
        this.idServer = exerciseExecution.getId();
        this.idServerPlanList = idPlanList;
        this.idServerTraining = idTraining;
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

    public int getExerciseId()
    {
        return exerciseId;
    }

    public int getIdServer()
    {
        return idServer;
    }

    public void setIdServer(int idServer)
    {
        this.idServer = idServer;
    }

    public int getIdServerPlanList()
    {
        return idServerPlanList;
    }

    public void setIdServerPlanList(int idServerPlanList)
    {
        this.idServerPlanList = idServerPlanList;
    }

    public int getIdServerTraining()
    {
        return idServerTraining;
    }

    public void setIdServerTraining(int idServerTraining)
    {
        this.idServerTraining = idServerTraining;
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
}
