package info.micdm.ftr.utils;

/**
 * Класс для более лучшего контроля за логированием.
 * @author Mic, 2011
 *
 */
public class Log {

	/**
	 * Тег для всех сообщений.
	 */
	public final static String TAG = "FTR";
	
	/**
	 * Выводит отладочное сообщение.
	 */
	public static synchronized void debug(String msg) {
		android.util.Log.d(TAG, msg);
	}
	
	/**
	 * Выводит информационное сообщение.
	 */
	public static synchronized void info(String msg) {
		android.util.Log.i(TAG, msg);
	}
	
	/**
	 * Выводит предупреждение.
	 */
	public static synchronized void warning(String msg) {
		android.util.Log.w(TAG, msg);
	}
	
	/**
	 * Выводит сообщение об ошибке.
	 */
	public static synchronized void error(String msg) {
		android.util.Log.e(TAG, msg);
	}
	
	/**
	 * Выводит сообщение об ошибке + распечатывает исключение.
	 */
	public static synchronized void error(String msg, Throwable e) {
		android.util.Log.e(TAG, msg, e);
	}
}
