package info.micdm.ftr;

import java.util.HashMap;

import android.util.Log;

/**
 * Список рубрик.
 * @author Mic, 2011
 *
 */
public class ThemeGroups {

	/**
	 * Синглтон.
	 */
	protected static ThemeGroups _instance;
	
	/**
	 * Список рубрик.
	 */
	protected HashMap<Integer, ThemeGroup> _groups = new HashMap<Integer, ThemeGroup>();
	
	/**
	 * Синглтон.
	 */
	public static ThemeGroups getInstance() {
		if (_instance == null) {
			_instance = new ThemeGroups();
		}
		return _instance;
	}
	
	/**
	 * Возвращает группу по идентификатору.
	 */
	public ThemeGroup getGroup(Integer id) {
		if (_groups.containsKey(id)) {
			return _groups.get(id);
		}
		return null;
	}
	
	/**
	 * Добавляет группу в список.
	 */
	public void addGroup(ThemeGroup group) {
		Integer id = group.getId();
		if (getGroup(id) == null) {
			Log.d(toString(), "group " + id + " added");
			_groups.put(id, group);
		} else {
			Log.w(toString(), "group " + id + " already added");
		}
	}
}
