package com.jezire.activity;

import java.util.concurrent.TimeUnit;

import com.jezire.App;
import com.jezire.R;
import com.jezire.widget.IconView;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ActivityVideoPlayer extends Activity {
	private SurfaceView surfaceView;
	private TextView txtCurrentTime, txtTotalTime;
	private SeekBar seekBar;
	private IconView btnPlayPause, btnPrev, btnNext, btnClose;
	private RelativeLayout layoutControl;
	private ImageView imgLoading;

	private SurfaceHolder surfaceHolder;
	private MediaPlayer mediaPlayer;

	private Handler progressHandler;
	private Runnable progressRunnable;

	private Handler autoHideHandler;
	private Runnable autoHideRunnable;

	boolean isPlayed = false;

	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.animation_in_fade, R.anim.animation_out_fade);

		setContentView(R.layout.layout_activity_video_player);
		initialize();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.animation_in_fade, R.anim.animation_out_fade);
	}

	@Override
	protected void onDestroy() {
		stop();
		super.onDestroy();
	}

	private void initialize() {
		if (getIntent().getExtras() == null) {
			onBackPressed();
		}

		url = getIntent().getExtras().getString("url");

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		txtCurrentTime = (TextView) findViewById(R.id.txtCurrentTime);
		txtTotalTime = (TextView) findViewById(R.id.txtTotalTime);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		btnPlayPause = (IconView) findViewById(R.id.btnPlayPause);
		btnPrev = (IconView) findViewById(R.id.btnPrev);
		btnNext = (IconView) findViewById(R.id.btnNext);
		btnClose = (IconView) findViewById(R.id.btnClose);
		layoutControl = (RelativeLayout) findViewById(R.id.layoutControl);
		imgLoading = (ImageView) findViewById(R.id.imgLoading);

		btnPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isPlayed) {
					progressHandler.removeCallbacks(progressRunnable);
					autoHideHandler.removeCallbacks(autoHideRunnable);
					int p = mediaPlayer.getCurrentPosition();
					if (p - 5000 >= 0) {
						mediaPlayer.seekTo(p - 5000);
					}
					updateProgress();
					autoHideControl();
				}
			}
		});

		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isPlayed) {
					progressHandler.removeCallbacks(progressRunnable);
					autoHideHandler.removeCallbacks(autoHideRunnable);
					int p = mediaPlayer.getCurrentPosition();
					if (p + 5000 <= mediaPlayer.getDuration()) {
						mediaPlayer.seekTo(p + 5000);
					}
					updateProgress();
					autoHideControl();
				}
			}
		});

		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		progressHandler = new Handler();
		progressRunnable = new Runnable() {
			public void run() {
				int position = mediaPlayer.getCurrentPosition();
				int duration = mediaPlayer.getDuration();

				if (position > duration) {
					setTime(txtCurrentTime, duration);
					seekBar.setProgress(duration);
				} else {
					setTime(txtCurrentTime, position);
					seekBar.setProgress(position);
				}

				setTime(txtTotalTime, duration);
				seekBar.setMax(duration);

				progressHandler.postDelayed(this, 1000);
			}
		};

		autoHideHandler = new Handler();
		autoHideRunnable = new Runnable() {
			public void run() {
				layoutControl.setVisibility(View.INVISIBLE);
			}
		};

		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				if (isPlayed) {
					progressHandler.removeCallbacks(progressRunnable);
					autoHideHandler.removeCallbacks(autoHideRunnable);
				}
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser && isPlayed) {
					setTime(txtCurrentTime, progress);
				}
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if (isPlayed) {
					progressHandler.removeCallbacks(progressRunnable);
					autoHideHandler.removeCallbacks(autoHideRunnable);
					mediaPlayer.seekTo(seekBar.getProgress());
					updateProgress();
					autoHideControl();
				}
			}
		});

		mediaPlayer = new MediaPlayer();
		mediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent) {
				seekBar.setSecondaryProgress((int) ((float) percent / 100F * (float) mediaPlayer.getDuration()));
			}
		});
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				isPlayed = true;
				mediaPlayer.start();
				imgLoading.clearAnimation();
				imgLoading.setVisibility(View.GONE);
				updateProgress();
				autoHideControl();
				btnPlayPause.setText("\ue018");
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				isPlayed = false;
				progressHandler.removeCallbacks(progressRunnable);
				autoHideHandler.removeCallbacks(autoHideRunnable);
				layoutControl.setVisibility(View.VISIBLE);
				seekBar.setProgress(0);
				seekBar.setMax(0);
				setTime(txtCurrentTime, 0);
				setTime(txtTotalTime, 0);
				imgLoading.clearAnimation();
				imgLoading.setVisibility(View.GONE);
				btnPlayPause.setText("\ue017");
			}
		});
		mediaPlayer.setOnErrorListener(new OnErrorListener() {
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				isPlayed = false;
				progressHandler.removeCallbacks(progressRunnable);
				autoHideHandler.removeCallbacks(autoHideRunnable);
				layoutControl.setVisibility(View.VISIBLE);
				seekBar.setProgress(0);
				seekBar.setMax(0);
				setTime(txtCurrentTime, 0);
				setTime(txtTotalTime, 0);
				imgLoading.clearAnimation();
				imgLoading.setVisibility(View.GONE);
				btnPlayPause.setText("\ue017");

				return false;
			}
		});

		surfaceView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				autoHideHandler.removeCallbacks(autoHideRunnable);
				layoutControl.setVisibility(View.VISIBLE);
				autoHideControl();
			}
		});

		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				mediaPlayer.setDisplay(surfaceHolder);
				play();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
			}
		});

		btnPlayPause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btnPlayPause.getText().toString().equals("\ue017")) {
					play();
					btnPlayPause.setText("\ue018");
				} else {
					pause();
					btnPlayPause.setText("\ue017");
				}
			}
		});
	}

	void setTime(TextView tv, int ms) {
		tv.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(ms), TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms))));
	}

	void updateProgress() {
		progressHandler.postDelayed(progressRunnable, 1000);
	}

	void autoHideControl() {
		autoHideHandler.postDelayed(autoHideRunnable, 3000);
	}

	void pause() {
		if (isPlayed) {
			progressHandler.removeCallbacks(progressRunnable);
			mediaPlayer.pause();
		}
	}

	void play() {
		try {
			if (url != null) {
				if (isPlayed) {
					updateProgress();
					mediaPlayer.start();
				} else {
					seekBar.setProgress(0);
					seekBar.setMax(0);
					imgLoading.setVisibility(View.VISIBLE);
					App.startLoadingAnimation(imgLoading);
					mediaPlayer.reset();
					mediaPlayer.setDataSource(url);
					mediaPlayer.prepareAsync();
				}
			}
		} catch (Exception e) {
		}
	}

	public void stop() {
		try {
			url = null;
			isPlayed = false;
			progressHandler.removeCallbacks(progressRunnable);
			autoHideHandler.removeCallbacks(autoHideRunnable);
			mediaPlayer.stop();
			mediaPlayer.release();
			seekBar.setProgress(0);
			seekBar.setMax(0);
			setTime(txtCurrentTime, 0);
			setTime(txtTotalTime, 0);
			layoutControl.setVisibility(View.VISIBLE);
			imgLoading.clearAnimation();
			imgLoading.setVisibility(View.GONE);
		} catch (Exception e) {
		}
	}
}