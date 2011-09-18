package info.micdm.ftr;

import info.micdm.ftr.async.DownloadTask;

import java.util.ArrayList;

import android.util.Log;

/**
 * Одна рубрика со списком тем.
 * @author Mic, 2011
 *
 */
public class ThemeGroup implements Comparable<ThemeGroup> {

	/**
	 * Коллбэк после загрузки списка тем.
	 * @author Mic, 2011
	 *
	 */
	public interface Command {
		public void callback(ArrayList<Theme> themes);
	}
	
	/**
	 * Идентификатор группы.
	 */
	protected Integer _id;
	
	/**
	 * Заголовок группы.
	 */
	protected String _title;
	
	/**
	 * Список тем в группе.
	 */
	protected ArrayList<Theme> _themes;

	public ThemeGroup(Integer id, String title) {
		_id = id;
		_title = title;
	}
	
	public String toString() {
		return _title;
	}
	
	public int compareTo(ThemeGroup another) {
		return _title.compareTo(another.getTitle());
	}
	
	/**
	 * Возвращает идентификатор группы.
	 */
	public Integer getId() {
		return _id;
	}
	
	/**
	 * Возвращает заголовок группы.
	 */
	public String getTitle() {
		return _title;
	}
	
	/**
	 * Возвращает темы внутри группы.
	 */
	public void getThemes(final Command command) {
		if (_themes == null) {
			DownloadTask task = new DownloadTask(new DownloadTask.Command() {
				public void callback(ArrayList<Theme> themes) {
					Log.d(toString(), themes.size() + " themes loaded");
					_themes = themes;
					command.callback(themes);
				}
			});
			task.execute("/forum/" + _id);
		} else {
			command.callback(_themes);
		}
	}
}
