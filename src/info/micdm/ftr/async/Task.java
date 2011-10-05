package info.micdm.ftr.async;

import android.os.AsyncTask;

/**
 * Абстрактная асинхронная задача.
 * @author Mic, 2011
 *
 */
public abstract class Task<Progress, Result> extends AsyncTask<Void, Progress, Result> {

	/**
	 * Для сигналов о завершении задачи.
	 * @author Mic, 2011
	 *
	 */
	public interface OnFinished {
		public void callback(Object result);
	}
	
	/**
	 * Для сигналов об отмене задачи.
	 * @author Mic, 2011
	 *
	 */
	public interface OnCancelled {
		public void callback();
	}
	
	/**
	 * Тут должен быть результат работы.
	 */
	protected Result _result;
	
	/**
	 * Выполнится по окончании задачи.
	 */
	protected OnFinished _onFinished;
	
	/**
	 * Выполнится при отмене задачи
	 */
	protected OnCancelled _onCancelled;
	
	/**
	 * Задает объекты для обратного вызова.
	 */
	public void setCallbacks(OnFinished onFinished, OnCancelled onCancelled) {
		_onFinished = onFinished;
		_onCancelled = onCancelled;
	}
	
	/**
	 * Запускает задачу.
	 */
	public void run() {
		if (_result != null) {
			if (_onFinished != null) {
				_onFinished.callback(_result);
			}
		} else if (isCancelled()) {
			if (_onCancelled != null) {
				_onCancelled.callback();
			}
		} else {
			Status status = getStatus();
			if (status == Status.PENDING) {
				execute();
			}
		}
	}
	
	@Override
	protected void onPostExecute(Result result) {
		_result = result;
		if (_onFinished != null) {
			_onFinished.callback(result);
			setCallbacks(null, null);
		}
	}
	
	@Override
	protected void onCancelled() {
		if (_onCancelled != null) {
			_onCancelled.callback();
			setCallbacks(null, null);
		}
	}
}
