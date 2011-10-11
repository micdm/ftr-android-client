package info.micdm.ftr.activities;

import info.micdm.ftr.async.TaskManager;
import android.app.ListActivity;
import android.os.Bundle;

/**
 * Базовый класс для экранов.
 * @author Mic, 2011
 *
 */
public abstract class Activity extends ListActivity {

	/**
	 * Менеджер асинхронных задач.
	 */
	protected TaskManager _taskManager;
	
	protected void _setupTaskManager() {
		_taskManager = new TaskManager(this);
		Object task = getLastNonConfigurationInstance();
		if (task != null) {
			//_taskManager.run(task, onFinished);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_setupTaskManager();
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return _taskManager.retain();
	}
}
