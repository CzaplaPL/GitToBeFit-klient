package pl.gittobefit.database.entity.training;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pl.gittobefit.WorkoutDisplay.objects.ExerciseExecution;
import pl.gittobefit.WorkoutDisplay.objects.TrainingPlan;
import pl.gittobefit.database.pojo.ExerciseExecutionPOJODB;
import pl.gittobefit.user.User;

import static java.sql.Types.NULL;


@Entity
public class SavedTraining
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String idUser;
    private long idForm;
    private long idFromServer;
    private ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList;
    private String generationDate;
    private String trainingName;
    private int trainingDay;
    private boolean offline;
    private int breakTime;
    private int circuitsCount;

    public SavedTraining(long idFromServer, long idForm, ArrayList<TrainingPlan> planList, String name, String date)
    {
        this.planList = new ArrayList<>();
        if(User.getInstance().getLoggedBy() == User.WayOfLogin.NO_LOGIN)
        {
            this.idUser = "";
            this.offline = true;
        }else
        {
            this.idUser = User.getInstance().getIdServer();
            this.offline = false;
        }
        this.breakTime = planList.get(0).getBreakTime();
        this.circuitsCount = planList.get(0).getCircuitsCount();
        this.generationDate = date;
        this.trainingName = name;
        this.idFromServer = idFromServer;
        this.idForm = idForm;
        for(TrainingPlan readPlan : planList)
        {
            ArrayList<ExerciseExecutionPOJODB> savePlan = new ArrayList<>();
            for(ExerciseExecution exercisesExecution : readPlan.getExercisesExecutions())
            {
                savePlan.add(new ExerciseExecutionPOJODB(exercisesExecution, readPlan.getId(), readPlan.getTrainingId()));
            }
            this.planList.add(savePlan);
        }

        this.trainingDay = 0;
    }

    public SavedTraining()
    {
        this.trainingDay = 0;
    }

    public SavedTraining(int circuitsCount, long breakTime)
    {
        this.circuitsCount = circuitsCount;
        this.breakTime = (int) breakTime;
        this.planList = new ArrayList<>();
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public ArrayList<ArrayList<ExerciseExecutionPOJODB>> getPlanList()
    {
        return this.planList;
    }

    public void setPlanList(ArrayList<ArrayList<ExerciseExecutionPOJODB>> planList)
    {
        this.planList = planList;
    }

    public long getIdForm()
    {
        return idForm;
    }

    public void setIdForm(long idForm)
    {
        this.idForm = idForm;
    }

    public String getIdUser()
    {
        return idUser;
    }

    public void setIdUser(String idUser)
    {
        this.idUser = idUser;
    }

    public String getGenerationDate()
    {
        return generationDate;
    }

    public void setGenerationDate(String generationDate)
    {
        this.generationDate = generationDate;
    }

    public String getTrainingName()
    {
        return trainingName;
    }

    public void setTrainingName(String trainingName)
    {
        this.trainingName = trainingName;
    }

    public int getTrainingDay()
    {
        return trainingDay;
    }

    public void setTrainingDay(int trainingDay)
    {
        this.trainingDay = trainingDay;
    }

    public boolean isNextDay()
    {
        return planList.size() > trainingDay + 1;
    }

    public long getIdFromServer()
    {
        return idFromServer;
    }

    public void setIdFromServer(long idFromServer)
    {
        this.idFromServer = idFromServer;
    }

    public int getBreakTime()
    {
        return breakTime;
    }

    public void setBreakTime(int breakTime)
    {
        this.breakTime = breakTime;
    }

    public int getCircuitsCount()
    {
        return circuitsCount;
    }

    public void setCircuitsCount(int circuitsCount)
    {
        this.circuitsCount = circuitsCount;
    }

    public boolean isOffline()
    {
        return offline;
    }

    public void setOffline(boolean offline)
    {
        this.offline = offline;
    }

    public void addDay(ArrayList<ExerciseExecutionPOJODB> trainingForDay)
    {
        planList.add(trainingForDay);
    }

    public void setInfo(String name)
    {
        if(User.getInstance().getLoggedBy() != User.WayOfLogin.NO_LOGIN)
        {
            this.idUser = User.getInstance().getIdServer();
        }else
        {
            this.idUser = "";
        }
        this.idFromServer = NULL;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.generationDate = formatter.format(date);
        this.trainingName = name;
        this.trainingDay = 0;
        this.offline = true;

    }
}
