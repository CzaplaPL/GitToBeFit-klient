package pl.gittobefit.running_training.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;

import pl.gittobefit.R;
import pl.gittobefit.databinding.FragmentTrainingStartBinding;
import pl.gittobefit.network.ConnectionToServer;
import pl.gittobefit.running_training.viewmodel.TrainingViewModel;

public class TrainingStart extends Fragment
{
    private TrainingViewModel model;
    private FragmentTrainingStartBinding binding;
    CountDownTimer timer;

    public TrainingStart()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null)
        {
            activity.getSupportActionBar().hide();
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    )
    {
        binding = FragmentTrainingStartBinding.inflate(inflater, container, false);
        model = new ViewModelProvider(this).get(TrainingViewModel.class);
        model.init(TrainingStartArgs.fromBundle(getArguments()).getDisplayToTraining(), getContext());
        generateMainView();
        binding.miss.setOnClickListener(v ->
        {
            if(model.nextExercise())
            {
                binding.readVideo.setVisibility(View.VISIBLE);
                binding.loaderVideo.setAlpha(0);
                generateMainView();
            }else
            {
                model.endTraining();
                Navigation.findNavController(getView()).navigate(R.id.action_trainingStart_to_homeFragment);
            }
        });
        binding.start.setOnClickListener(v ->
        {
            binding.miss.setEnabled(false);
            binding.start.setEnabled(false);
            binding.scrollViewDescription.setEnabled(false);
            generateForegroundView();
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView()
    {
        if(timer != null) timer.cancel();
        super.onDestroyView();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null)
        {
            activity.getSupportActionBar().show();
        }
    }

    private void generateMainView() // glowny widok prowadzenia treningu
    {
        getVideo();
        binding.titleExercise.setText(String.format("%s%s - %s",
                getString(R.string.exercise),
                model.getIndexExercise() + 1,
                model.getExercise().getName()
        ));
        binding.descriptionOfStartText.setText(model.getExercise().getDescriptionOfStartPosition());
        binding.descriptionOfMoveText.setText(model.getExercise().getDescriptionOfCorrectExecution());
        if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT"))
        {
            setupForCircuit();
        }else
        {
            setupForSeries();
        }
        setupForTime();
    }

    private void setupForTime()
    { // odroznienie czy to jest cwiczenie na czas, czy na serie
        if(model.getExerciseExecution().getCount() == 0)
        {
            binding.titleCountOfRepeats.setText(getString((R.string.timeTitle)));
            binding.countOfRepeats.setText(String.format("%s %s",
                    model.getExerciseExecution().getTime(),
                    getString(R.string.seconds)
            ));
        }else
        {
            binding.titleCountOfRepeats.setText(getString(R.string.repeatsTitle));
            binding.countOfRepeats.setText(String.valueOf(model.getExerciseExecution().getCount()));
        }
    }

    private void setupForCircuit()
    { // zmiana na obwodowy
        binding.scheduleTypeTitle.setText(getString(R.string.scheduleTypeCircuit));
        binding.countOfSeries.setText(String.format("%s / %s",
                model.getNumberOfSeries(),
                model.getTrainingWithForm().training.getCircuitsCount())
        );
    }

    private void setupForSeries()
    { // zmiana na seriowy
        binding.scheduleTypeTitle.setText(getString(R.string.seriesTitle));
        binding.countOfSeries.setText(String.valueOf(model.getExerciseExecution().getSeries()));
    }

    private void generateForegroundView()
    { // wczytanie okienka po kliknieciu start
        printDefault();
        if(model.getExerciseExecution().getCount() == 0)
        {
            binding.textInfoNext.setText(getString(R.string.preview_info));
            binding.timerPause.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.downtoup));
            binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.downtoup));
            timerBeforeStartTraining();
        }else
        {
            binding.textInfoNext.setText(getString(R.string.text_info_next));
            binding.printCountOfRepeats.setText(String.valueOf(model.getExerciseExecution().getCount()));
            binding.buttonClicker.setOnClickListener(v -> timerOnBreak());
        }
        if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT"))
        {
            binding.printScheduleTypeTitle.setText(getString(R.string.scheduleTypeCircuit));
        }else
        {
            binding.printScheduleTypeTitle.setText(getString(R.string.scheduleTypeSeries));
        }
    }

    private void printDefault()
    {
        if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT"))
        {
            binding.printCountOfSeries.setText(String.format("%s / %s",
                    model.getNumberOfSeries(),
                    model.getTrainingWithForm().training.getCircuitsCount()
            ));
        }else
        {
            binding.printCountOfSeries.setText(String.format("%s / %s",
                    model.getNumberOfSeries(),
                    model.getExerciseExecution().getSeries()
            ));
        }


        if(model.getExerciseExecution().getCount() == 0)
        {
            binding.buttonClickButton.setVisibility(View.GONE);
            binding.timerPause.setVisibility(View.VISIBLE);
            binding.printCount.setVisibility(View.GONE);
        }else
        {
            binding.buttonClickButton.setVisibility(View.VISIBLE);
            binding.printCount.setVisibility(View.VISIBLE);
            binding.timerPause.setVisibility(View.GONE);
        }
        binding.exerciseBackground.setVisibility(View.VISIBLE);
        binding.exerciseStart.setVisibility(View.VISIBLE);
        binding.exerciseStart.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.started));
        getSmallVideo();
        binding.printExerciseInfo.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.downtoup));
        binding.printExerciseInfo.setVisibility(View.VISIBLE);
    }

    private void timerBreakDuringExercises()
    {
        timer = new CountDownTimer(model.getTrainingWithForm().training.getBreakTime() * 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int seconds = (int) (millisUntilFinished / 1000);
                binding.start.setText(String.valueOf(seconds + 1));
            }

            @Override
            public void onFinish()
            {
                binding.start.setText(R.string.start);
                binding.start.setEnabled(true);
                binding.miss.setEnabled(true);
            }
        }.start();
    }

    private void timerBeforeStartTraining()
    {
        timer = new CountDownTimer(6000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int seconds = (int) (millisUntilFinished / 1000);
                binding.circleTimerPauseText.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish()
            {
                binding.timerPause.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
                binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
                binding.textInfoNext.setText(getString(R.string.textForTimerExercise));
                timerOnStartTraining();
            }
        }.start();
    }

    private void timerOnStartTraining()
    {
        timer = new CountDownTimer(model.getExerciseExecution().getTime() * 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int seconds = (int) (millisUntilFinished / 1000);
                binding.circleTimerPauseText.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish()
            {
                if((model.getNumberOfSeries() < model.getExerciseExecution().getSeries()))
                {
                    binding.timerPause.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
                    binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
                    binding.textInfoNext.setText(getString(R.string.textinfobreak));
                    timerOnBreak();
                }else
                {
                    exitExerciseForSeries();
                }
            }
        }.start();
    }

    private void exitExerciseForSeries()
    {
        if(model.nextExercise())
        {
            binding.exerciseBackground.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.exit));
            binding.exerciseStart.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.exit));
            binding.exerciseBackground.setVisibility(View.INVISIBLE);
            binding.exerciseStart.setVisibility(View.INVISIBLE);
            binding.scrollViewDescription.setEnabled(true);
            binding.miss.setEnabled(false);
            binding.start.setEnabled(false);
            binding.miss.setVisibility(View.VISIBLE);
            binding.start.setVisibility(View.VISIBLE);
            generateMainView();
            binding.miss.setEnabled(true);
            timerBreakDuringExercises();
        }else
        {
            model.endTraining();
            Navigation.findNavController(getView()).navigate(R.id.action_trainingStart_to_homeFragment);
        }
    }

    private void timerOnBreak()
    {
        if(model.getTrainingWithForm().form.getScheduleType().equals("CIRCUIT"))
        {
            exitExerciseForCircuit();
        }else
        {
            if(model.getNumberOfSeries() < model.getExerciseExecution().getSeries())
            {
                if(model.getExerciseExecution().getCount() != 0)
                {
                    binding.buttonClickButton.setVisibility(View.GONE);
                    binding.textInfoNext.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
                    binding.timerPause.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
                    binding.timerPause.setVisibility(View.VISIBLE);
                    binding.textInfoNext.setText(getString(R.string.textinfobreak));
                }
                startPause();
            }else
            {
                exitExerciseForSeries();
            }
        }
    }

    private void startPause()
    {
        timer = new CountDownTimer(model.getTrainingWithForm().training.getBreakTime() * 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                int seconds = (int) (millisUntilFinished / 1000);
                binding.circleTimerPauseText.setText(String.valueOf(seconds));
            }

            @Override
            public void onFinish()
            {
                model.nextSeries();
                nextSeriesOrCircuit();
            }
        }.start();
    }

    private void exitExerciseForCircuit()
    {
        if(model.nextExerciseForCircuit())
        {
            binding.exerciseBackground.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.exit));
            binding.exerciseStart.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.exit));
            binding.exerciseBackground.setVisibility(View.INVISIBLE);
            binding.exerciseStart.setVisibility(View.INVISIBLE);
            binding.scrollViewDescription.setEnabled(true);
            binding.miss.setEnabled(false);
            binding.start.setEnabled(false);
            binding.miss.setVisibility(View.VISIBLE);
            binding.start.setVisibility(View.VISIBLE);
            generateMainView();
            binding.miss.setEnabled(true);
            timerBreakDuringExercises();
        }else
        {
            model.endTraining();
            Navigation.findNavController(getView()).navigate(R.id.action_trainingStart_to_homeFragment);
        }
    }

    private void nextSeriesOrCircuit()
    {
        binding.printCountOfSeries.setText(String.format("%s / %s",
                String.valueOf(model.getNumberOfSeries()),
                String.valueOf(model.getExerciseExecution().getSeries())
        ));
        if(model.getExerciseExecution().getCount() == 0)
        {
            binding.timerPause.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
            binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
            binding.textInfoNext.setText(getString(R.string.textForTimerExercise));
            timerOnStartTraining();
        }else
        {
            binding.textInfoNext.setText(getString(R.string.text_info_next));
            binding.timerPause.setVisibility(View.GONE);
            binding.textInfoNext.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
            binding.buttonClickButton.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.righttoleft));
            binding.buttonClickButton.setVisibility(View.VISIBLE);
        }
    }

    private void getVideo()
    { // wczytanie wideo do maina
        if(model.getExercise().getVideoUrl() != null)
        {
            binding.image.setVisibility(View.INVISIBLE);
            binding.loaderVideo.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(ConnectionToServer.PREFIX_VIDEO_URL + model.getExercise().getVideoUrl());
            binding.loaderVideo.setVideoURI(uri);
            binding.loaderVideo.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
            binding.loaderVideo.setOnPreparedListener(mp ->
            {
                binding.readVideo.setVisibility(View.INVISIBLE);
                binding.loaderVideo.start();
                binding.loaderVideo.setAlpha(1);
                mp.setLooping(true);
            });
        }else
        {
            binding.loaderVideo.setVisibility(View.INVISIBLE);
            binding.readVideo.setVisibility(View.INVISIBLE);
            binding.image.setVisibility(View.VISIBLE);
            Glide.with(this).load(ConnectionToServer.PREFIX_PHOTO_URL + model.getExercise().getPhotoUrl()).into(binding.image);
        }
    }

    private void getSmallVideo()
    { // wczytanie wideo do foregrounda
        if(model.getExercise().getVideoUrl() != null)
        {
            binding.smallImage.setVisibility(View.INVISIBLE);
            binding.videoViewStartTraining.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(ConnectionToServer.PREFIX_VIDEO_URL + model.getExercise().getVideoUrl());
            binding.videoViewStartTraining.setVideoURI(uri);
            binding.videoViewStartTraining.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
            binding.videoViewStartTraining.setOnPreparedListener(mp ->
            {
                binding.videoViewStartTraining.start();
                mp.setLooping(true);
            });
        }else
        {
            binding.videoViewStartTraining.setVisibility(View.INVISIBLE);
            binding.smallImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(ConnectionToServer.PREFIX_PHOTO_URL + model.getExercise().getPhotoUrl()).into(binding.smallImage);
        }
    }
}
