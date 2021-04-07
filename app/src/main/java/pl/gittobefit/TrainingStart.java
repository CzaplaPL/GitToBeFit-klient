package pl.gittobefit;

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

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class TrainingStart extends Fragment  {

    private static final long START_TIME_IN_MILLIS = 13000;
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

    public TrainingStart() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_training_start, container, false);

        exerciseStart = view.findViewById(R.id.exercise_start);
        exerciseBackground = view.findViewById(R.id.exercise_background);

        buttonLayout = view.findViewById(R.id.buttonLayout);
        timerText = view.findViewById(R.id.textTime);

        miss = view.findViewById(R.id.miss);
        start = view.findViewById(R.id.start);
        nextExercise = view.findViewById(R.id.nextExercise);

        buttonPlay = view.findViewById(R.id.buttonPlay);
        buttonPause = view.findViewById(R.id.buttonPause);

        videoViewTraining = view.findViewById(R.id.videoViewTraining);
        videoViewStartTraining = view.findViewById(R.id.videoViewStartTraining);

        started = AnimationUtils.loadAnimation(getActivity(),R.anim.started);
        bright = AnimationUtils.loadAnimation(getActivity(),R.anim.bright);
        downtoup = AnimationUtils.loadAnimation(getActivity(),R.anim.downtoup);
        exit = AnimationUtils.loadAnimation(getActivity(),R.anim.exit);

        getVideo(videoViewTraining);

        start.setOnClickListener(v -> {
            videoViewTraining.setVisibility(View.INVISIBLE);
            miss.setEnabled(false);
            start.setEnabled(false);
            exerciseBackground.setVisibility(View.VISIBLE);
            exerciseStart.setVisibility(View.VISIBLE);
            exerciseBackground.startAnimation(bright);
            exerciseStart.startAnimation(started);
            getVideo(videoViewStartTraining);
            buttonLayout.startAnimation(downtoup);
            timerText.startAnimation(downtoup);
            startTimer();
        });

        buttonPause.setOnClickListener(v -> {
            pauseTimer();
            buttonPause.setVisibility(View.GONE);
            buttonPlay.setVisibility(View.VISIBLE);
        });

        buttonPlay.setOnClickListener(v -> {
            startTimer();
            buttonPause.setVisibility(View.VISIBLE);
            buttonPlay.setVisibility(View.GONE);
        });

        return view;
    }

    private void getVideo(VideoView v) {
        Uri uri = Uri.parse("https://static.fabrykasily.pl/atlas/video-unoszenie-bioder-w-gore-z-palcami-uniesionymi.mp4");
        v.setVideoURI(uri);
        v.start();
        v.setOnPreparedListener(mediaPlayer -> mediaPlayer.setLooping(true));
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
                miss.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                nextExercise.setVisibility(View.VISIBLE);
                exerciseBackground.startAnimation(exit);
                exerciseStart.startAnimation(exit);
                ViewCompat.animate(exerciseBackground).setStartDelay(1000).alpha(0).start();
                ViewCompat.animate(exerciseStart).setStartDelay(1000).alpha(0).start();
                videoViewTraining.setVisibility(View.VISIBLE);
                getVideo(videoViewTraining);
            }
        }.start();
        timerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        timerText.setText(timeFormatted);
    }
}
