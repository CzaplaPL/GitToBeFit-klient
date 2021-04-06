package pl.gittobefit.running_training.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentTrainingStartBinding;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.running_training.viewmodel.TrainingViewModel;

public class TrainingStart extends Fragment  {

    private static final long START_TIME_IN_MILLIS = 6000;
    private boolean timerRunning;
    private long timeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer timerAdapter;
    private TextView timerText;
    private View view;
    private ImageView buttonPlay, buttonPause;
    private LinearLayout exerciseStart, exerciseBackground;
    private RelativeLayout buttonLayout;
    private Animation started, exit, downtoup, bright;
    private Button miss, start, nextExercise;
    private VideoView videoViewTraining, videoViewStartTraining;


    private TrainingViewModel model;
    private FragmentTrainingStartBinding binding;

    public TrainingStart() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().hide();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_training_start, container, false);

        binding = FragmentTrainingStartBinding.inflate(inflater, container, false);
        model =  new ViewModelProvider(this).get(TrainingViewModel.class);
        model.init(TrainingStartArgs.fromBundle(getArguments()).getDisplayToTraining(),getContext());
        generateView();

        started = AnimationUtils.loadAnimation(getActivity(),R.anim.started);
        bright = AnimationUtils.loadAnimation(getActivity(),R.anim.bright);
        downtoup = AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup);
        exit = AnimationUtils.loadAnimation(getActivity(),R.anim.exit);

        /**/

        binding.miss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.nextExercise()){
                    binding.loaderVideo.setVisibility(View.VISIBLE);
                    binding.videoViewTraining.setAlpha(0);
                    generateView();
                }
                else{
                    // do przegadania !!!!!!!!!
                }
            }
        });

        binding.nextExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.nextExercise()){
                    binding.loaderVideo.setVisibility(View.VISIBLE);
                    binding.videoViewTraining.setAlpha(0);
                    generateView();
                }
                else{
                    // do przegadania !!!!!!!!!
                }
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.videoViewTraining.setVisibility(View.INVISIBLE);
                binding.miss.setEnabled(false);
                binding.start.setEnabled(false);
                binding.exerciseBackground.setVisibility(View.VISIBLE);
                binding.exerciseStart.setVisibility(View.VISIBLE);
                binding.exerciseBackground.startAnimation(bright);
                binding.exerciseStart.startAnimation(started);
                binding.buttonLayout.startAnimation(downtoup);
                getSmallVideo();
                binding.timerWaitForStart.startAnimation(downtoup);
                startCircleTimer();
            }
        });

        /**/

        /*buttonPause.setOnClickListener(v -> {
            pauseTimer();
            buttonPause.setVisibility(View.GONE);
            buttonPlay.setVisibility(View.VISIBLE);
        });

        buttonPlay.setOnClickListener(v -> {
            startTimer();
            buttonPause.setVisibility(View.VISIBLE);
            buttonPlay.setVisibility(View.GONE);
        });*/

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.getSupportActionBar().show();
        }
    }

    private void generateView()
    {
        getVideo();
        binding.titleExercise.setText(getString(R.string.exercise) + String.valueOf(model.getIndexExercise() + 1) + " - " + model.getExercise().getName());
        binding.descriptionOfStartText.setText(model.getExercise().getDescriptionOfStartPosition());
        binding.descriptionOfMoveText.setText(model.getExercise().getDescriptionOfCorrectExecution());

        if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT")){
            setupForCircuit();
            }
            else {
            setupForSeries();
        }
        setupForTime();
    }

    private void setupForTime(){
        if(model.getExerciseExecution().getCount() == 0){
            binding.titleCountOfRepeats.setText(getString((R.string.timeTitle)));
            binding.countOfRepeats.setText(String.valueOf(model.getExerciseExecution().getTime()) + " sekund") ;
        }
        else{
            binding.titleCountOfRepeats.setText(getString(R.string.repeatsTitle));
            binding.countOfRepeats.setText(String.valueOf(model.getExerciseExecution().getCount()));
        }
    }

    private void setupForCircuit() {
        binding.scheduleTypeTitle.setText(getString(R.string.scheduleTypeCircuit));
        binding.countOfSeries.setText(String.valueOf(model.getNumberOfSeries() + " / " + String.valueOf(model.getExerciseExecution().getSeries())));
    }

    private void setupForSeries(){
        binding.scheduleTypeTitle.setText(getString(R.string.scheduleTypeSeries));
        binding.countOfSeries.setText(String.valueOf(model.getNumberOfSeries()) + " / " + String.valueOf(model.getExerciseExecution().getSeries()));
    }

    private void getVideo() {
        Uri uri = Uri.parse(ConnectionToServer.getInstance().PREFIX_VIDEO_URL + model.getExercise().getVideoUrl());
        binding.videoViewTraining.setVideoURI(uri);
        binding.videoViewTraining.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
        binding.videoViewTraining.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                binding.videoViewTraining.start();
                binding.loaderVideo.setVisibility(View.GONE);
                binding.videoViewTraining.setAlpha(1);
                mp.setLooping(true);
            }
        });
    }

    private void getSmallVideo(){
        Uri uri = Uri.parse(ConnectionToServer.getInstance().PREFIX_VIDEO_URL + model.getExercise().getVideoUrl());
        binding.videoViewStartTraining.setVideoURI(uri);
        binding.videoViewStartTraining.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
        binding.videoViewStartTraining.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                binding.videoViewStartTraining.start();
                mp.setLooping(true);
            }
        });
    }

    private void pauseTimer() {
        timerAdapter.cancel();
        timerRunning = false;
    }

    private void startTimer() {
        timerAdapter = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                binding.miss.setVisibility(View.GONE);
                binding.start.setVisibility(View.GONE);
                binding.exerciseBackground.startAnimation(exit);
                binding.exerciseStart.startAnimation(exit);
                ViewCompat.animate(binding.exerciseBackground).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(binding.exerciseStart).setStartDelay(1000).alpha(0).start();
                binding.videoViewTraining.setVisibility(View.VISIBLE);
                binding.nextExercise.setVisibility(View.VISIBLE);
            }
        }.start();
        timerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        binding.textTime.setText(timeFormatted);
    }

    private void startCircleTimer() {
        new CountDownTimer(9000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                String timeFormatted = String.valueOf(seconds);
                binding.circleTimerText.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                binding.timerWaitForStart.setVisibility(View.GONE);
                startTrainingOnCreate();
            }
        }.start();
        timerRunning = true;
    }

    private void startTrainingOnCreate(){

    }
}
