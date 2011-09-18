package info.micdm.ftr;

import info.micdm.ftr.async.DownloadTask;

import java.util.ArrayList;

import android.util.Log;

/**
 * Одна рубрика со списком тем.
 * @author Mic, 2011
 *
 */
public class ThemeGroup {

	/**
	 * Коллбэк после загрузки списка тем.
	 * @author Mic, 2011
	 *
	 */
	public interface Command {
		public void callback(ThemeGroup group);
	}
	
	/**
	 * Идентификатор группы.
	 */
	protected Integer _id;
	
	/**
	 * Список тем в группе.
	 */
	protected ArrayList<Theme> _themes;
	
	/**
	 * Загружает группу тем.
	 */
	public static void load(final Integer id, final Command command) {
		DownloadTask task = new DownloadTask(new DownloadTask.Command() {
			public void callback(ArrayList<Theme> themes) {
				Log.d(toString(), themes.size() + " themes loaded");
				ThemeGroup group = new ThemeGroup(id, themes);
				command.callback(group);
			}
		});
		task.execute("/forum/" + id);
	}
	
	public ThemeGroup(Integer id, ArrayList<Theme> themes) {
		_id = id;
		_themes = themes;
	}
	
	/**
	 * Возвращает идентификатор группы.
	 */
	public Integer getId() {
		return _id;
	}
	
	/**
	 * Возвращает темы внутри группы.
	 */
	public ArrayList<Theme> getThemes() {
		return _themes;
	}
}
