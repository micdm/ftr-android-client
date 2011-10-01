package info.micdm.ftr;

import info.micdm.ftr.async.DownloadThemePageTask;

import java.util.Date;
import java.util.HashMap;

import android.text.format.DateUtils;

/**
 * Класс темы.
 * @author Mic, 2011
 *
 */
public class Theme {

	public interface OnPageLoadedCommand {
		public void callback(ThemePage page);
	}
	
	/**
	 * Идентификатор группы, в которой находится тема.
	 */
	protected Integer _groupId;
	
	/**
	 * Идентификатор темы.
	 */
	protected Integer _id;
	
	/**
	 * Время последнего обновления темы.
	 */
	protected Date _updated;
	
	/**
	 * Автор темы.
	 */
	protected String _author;
	
	/**
	 * Название темы.
	 */
	private String _title;

	/**
	 * Количество страниц в теме.
	 */
	protected Integer _pageCount;
	
	/**
	 * Загруженные страницы.
	 */
	protected HashMap<Integer, ThemePage> _pages = new HashMap<Integer, ThemePage>();
	
	public Theme(Integer groupId, Integer id, Date updated, String author, String title) {
		_groupId = groupId;
		_id = id;
		_updated = updated;
		_author = author;
		_title = title;
	}

	@Override
	public String toString() {
		String updated = DateUtils.getRelativeTimeSpanString(_updated.getTime()).toString();
		return _author + ", " + updated + "\n" + getTitle();
	}
	
	/**
	 * Возвращает идентификатор группы.
	 */
	public Integer getGroupId() {
		return _groupId;
	}
	
	/**
	 * Возвращает идентификатор темы.
	 */
	public Integer getId() {
		return _id;
	}
	
	/**
	 * Возвращает заголовок темы.
	 */
	public String getTitle() {
		return _title;
	}
	
	/**
	 * Возвращает количество страниц.
	 */
	public Integer getPageCount() {
		return 3;
		//return _pageCount;
	}
	
	/**
	 * Загружает одну страницу и делает обратный вызов.
	 */
	public void loadPage(final Integer pageNumber, final OnPageLoadedCommand onLoad) {
		if (_pages.containsKey(pageNumber)) {
			onLoad.callback(_pages.get(pageNumber));
		} else {
			DownloadThemePageTask task = new DownloadThemePageTask(this, pageNumber) {
				@Override
				public void onPostExecute(ThemePage page) {
					_pages.put(pageNumber, page);
					onLoad.callback(page);
				}
			};
			task.execute();
		}
	}
}
