package info.micdm.ftr;

import info.micdm.ftr.async.DownloadGroupPageTask;

import java.util.ArrayList;

import android.util.Log;

/**
 * Одна рубрика со списком тем.
 * 
 * @author Mic, 2011
 * 
 */
public class Group implements Comparable<Group> {

	/**
	 * Коллбэк после загрузки списка тем.
	 * 
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
	protected ArrayList<Theme> _themes = new ArrayList<Theme>();

	public Group(Integer id, String title) {
		_id = id;
		_title = title;
	}

	@Override
	public String toString() {
		return _title;
	}

	@Override
	public int compareTo(Group another) {
		if (_id.equals(0)) {
			return -1;
		}
		if (another.getId().equals(0)) {
			return 1;
		}
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
	 * Возвращает тему по идентификатору.
	 */
	public Theme getTheme(Integer id) {
		// TODO: использовать HashMap?
		for (Theme theme: _themes) {
			if (id.equals(theme.getId())) {
				return theme;
			}
		}
		return null;
	}

	/**
	 * Возвращает темы внутри группы.
	 */
	public void getThemes(final Command command) {
		DownloadGroupPageTask task = new DownloadGroupPageTask(_id) {
			@Override
			public void onPostExecute(ArrayList<Theme> themes) {
				Log.d(toString(), themes.size() + " themes loaded");
				for (Theme theme: themes) {
					Group group = Forum.getInstance().getGroup(theme.getGroupId());
					group.addTheme(theme);
				}
				command.callback(themes);
			}
		};
		task.execute();
	}
	
	public void addTheme(Theme theme) {
		_themes.add(theme);
	}
}
