package pl.gittobefit.running_training.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentTrainingStartBinding;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.running_training.viewmodel.TrainingViewModel;

public class TrainingStart extends Fragment  {
    private TrainingViewModel model;
    private FragmentTrainingStartBinding binding;
    private int getSeries;

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
        binding = FragmentTrainingStartBinding.inflate(inflater, container, false);
        model =  new ViewModelProvider(this).get(TrainingViewModel.class);
        model.init(TrainingStartArgs.fromBundle(getArguments()).getDisplayToTraining(),getContext());
        generateMainView();

        binding.miss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.nextExercise()){
                    binding.readVideo.setVisibility(View.VISIBLE);
                    binding.loaderVideo.setAlpha(0);
                    generateMainView();
                }
                else{
                    // do przegadania !!!!!!!!!
                }
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.miss.setEnabled(false);
                binding.start.setEnabled(false);
                generateForegroundView();
            }
        });

       /* binding.nextExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.nextExercise()){
                    generateMainView();
                    binding.readVideo.setVisibility(View.VISIBLE);
                    binding.loaderVideo.setAlpha(0);
                    binding.nextExercise.setVisibility(View.GONE);
                    binding.miss.setVisibility(View.VISIBLE);
                    binding.start.setVisibility(View.VISIBLE);
                    binding.miss.setEnabled(true);
                    binding.start.setEnabled(true);
                }
            }
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

    private void generateMainView() // glowny widok prowadzenia treningu
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

    private void generateForegroundView(){ // wczytanie okienka po kliknieciu start
        getSmallVideo();
        if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT")){
            binding.printScheduleTypeTitle.setVisibility(View.INVISIBLE);
            binding.printCountOfSeries.setVisibility(View.INVISIBLE);
        }
        binding.exerciseBackground.setVisibility(View.VISIBLE);
        binding.exerciseBackground.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.bright));
        binding.exerciseStart.setVisibility(View.VISIBLE);
        binding.exerciseStart.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.started));
        if(model.getExerciseExecution().getCount() == 0){
            binding.timerWaitForStart.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup));
            binding.timerWaitForStart.setVisibility(View.VISIBLE);
            startCircleTimer();
        }
        else{
            binding.timerPause.setVisibility(View.GONE);
            binding.textInfoNext.setText(getString(R.string.text_info_next));
            binding.buttonClicker.setVisibility(View.VISIBLE);
            binding.printExerciseInfo.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup));
            binding.printExerciseInfo.setVisibility(View.VISIBLE);
            binding.printCountOfRepeats.setText(String.valueOf(model.getExerciseExecution().getCount()));

            binding.buttonClicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT")){
                        binding.miss.setVisibility(View.INVISIBLE);
                        binding.start.setVisibility(View.INVISIBLE);
                        binding.exerciseBackground.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.exit));
                        binding.exerciseStart.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.exit));
                        binding.exerciseBackground.setVisibility(View.INVISIBLE);
                        binding.exerciseStart.setVisibility(View.INVISIBLE);
                        if(model.nextExerciseForCircuit()) {
                            binding.readVideo.setVisibility(View.VISIBLE);
                            binding.loaderVideo.setAlpha(0);
                            binding.miss.setEnabled(true);
                            binding.start.setEnabled(true);
                            binding.miss.setVisibility(View.VISIBLE);
                            binding.start.setVisibility(View.VISIBLE);
                            generateMainView();
                        }
                    }
                    else{
                        binding.buttonClicker.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.lefttoright));
                        binding.buttonClicker.setVisibility(View.GONE);
                        binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.lefttoright));
                        binding.textInfoNext.setText(getString(R.string.textinfobreak));
                        binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.righttoleft));
                        binding.timerPause.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.righttoleft));
                        binding.timerPause.setVisibility(View.VISIBLE);
                        if(model.getNumberOfSeries() < model.getExerciseExecution().getSeries()){
                            startCircleTimerPause();
                        }
                        else{
                            binding.miss.setVisibility(View.INVISIBLE);
                            binding.start.setVisibility(View.INVISIBLE);
                            binding.exerciseBackground.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.exit));
                            binding.exerciseStart.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.exit));
                            binding.exerciseBackground.setVisibility(View.INVISIBLE);
                            binding.exerciseStart.setVisibility(View.INVISIBLE);
                            if(model.nextExercise()){
                                binding.readVideo.setVisibility(View.VISIBLE);
                                binding.loaderVideo.setAlpha(0);
                                binding.miss.setEnabled(true);
                                binding.start.setEnabled(true);
                                binding.miss.setVisibility(View.VISIBLE);
                                binding.start.setVisibility(View.VISIBLE);
                                generateMainView();
                            }
                        }
                    }

                }
            });
        }
        binding.printCountOfSeries.setText(String.valueOf(model.getNumberOfSeries()) + " / " + String.valueOf(model.getExerciseExecution().getSeries()));
        if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT")){
            binding.printScheduleTypeTitle.setText(getString(R.string.scheduleTypeCircuit));
        }
        else{
            binding.printScheduleTypeTitle.setText(getString(R.string.scheduleTypeSeries));
        }
    }

    private void setupForTime(){ // odroznienie czy to jest cwiczenie na czas, czy na serie
        if(model.getExerciseExecution().getCount() == 0){
            binding.titleCountOfRepeats.setText(getString((R.string.timeTitle)));
            binding.countOfRepeats.setText(String.valueOf(model.getExerciseExecution().getTime()) + " sekund") ;
        }
        else{
            binding.titleCountOfRepeats.setText(getString(R.string.repeatsTitle));
            binding.countOfRepeats.setText(String.valueOf(model.getExerciseExecution().getCount()));
        }
    }

    private void setupForCircuit() { // zmiana na obwodowy
        binding.scheduleTypeTitle.setText(getString(R.string.scheduleTypeCircuit));
        binding.countOfSeries.setText(String.valueOf(model.getNumberOfSeries()) + " / " + String.valueOf(model.getExerciseExecution().getSeries()));
    }

    private void setupForSeries(){ // zmiana na seriowy
        binding.scheduleTypeTitle.setText(getString(R.string.seriesTitle));
        binding.countOfSeries.setText(String.valueOf(model.getExerciseExecution().getSeries()));
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
                binding.preview.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.lefttoright));
                binding.circleTimerText.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.lefttoright));
                binding.preview.setText(getString(R.string.textForTimerExercise));
                binding.preview.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.righttoleft));
                binding.circleTimerText.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.righttoleft));
                startCircleTimerForTraining();
            }
        }.start();
    }

    private void startCircleTimerForTraining() {
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                String timeFormatted = String.valueOf(seconds);
                binding.circleTimerText.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                binding.preview.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.lefttoright));
                binding.preview.setText(getString(R.string.preview_info));
                binding.preview.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.righttoleft));
                startCircleTimer();
            }
        }.start();
    }

    private void startCircleTimerPause() {
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                String timeFormatted = String.valueOf(seconds);
                binding.circleTimerPauseText.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                    model.nextSeries();
                    binding.printCountOfSeries.setText(String.valueOf(model.getNumberOfSeries()) + " / " + String.valueOf(model.getExerciseExecution().getSeries()));
                    binding.timerPause.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.lefttoright));
                    binding.timerPause.setVisibility(View.GONE);
                    binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.lefttoright));
                    binding.textInfoNext.setText(getString(R.string.text_info_next));
                    binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.righttoleft));
                    binding.buttonClicker.setVisibility(View.VISIBLE);
                    binding.buttonClicker.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.righttoleft));
                }
        }.start();
    }

    private void getVideo() { // wczytanie wideo do maina
        Uri uri = Uri.parse(ConnectionToServer.getInstance().PREFIX_VIDEO_URL + model.getExercise().getVideoUrl());
        binding.loaderVideo.setVideoURI(uri);
        binding.loaderVideo.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
        binding.loaderVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                binding.readVideo.setVisibility(View.INVISIBLE);
                binding.loaderVideo.start();
                binding.loaderVideo.setAlpha(1);
                mp.setLooping(true);
            }
        });
    }

    private void getSmallVideo(){ // wczytanie wideo do foregrounda
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
}
