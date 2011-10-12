package info.micdm.ftr.activities;

import info.micdm.ftr.async.TaskManager;
import android.app.ListActivity;
import android.os.Bundle;

/**
 * Базовый класс для экранов, на которых выполняются фоновые задачи
 * @author Mic, 2011
 *
 */
public abstract class TaskActivity extends ListActivity {

	/**
	 * Менеджер асинхронных задач.
	 */
	protected TaskManager _taskManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_taskManager = new TaskManager(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		_taskManager.cancel();
		_taskManager = null;
	}
}
