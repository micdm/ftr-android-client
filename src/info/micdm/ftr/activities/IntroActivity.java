package info.micdm.ftr.activities;

import info.micdm.ftr.R;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Стартовый экран.
 * @author Mic, 2011
 *
 */
public class IntroActivity extends Activity {

	/**
	 * Переходит в основное окно.
	 */
	protected void _onGoTimer() {
		Log.d(toString(), "coming to groups activity");
		Intent intent = new Intent(this, ForumActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Запускает таймер, по которому будет переход в основное окно.
	 */
	protected void _setGoTimer(Long delay) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				_onGoTimer();
			}
		}, delay);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		_setGoTimer(3000L);
	}
}
