package info.micdm.ftr;

import info.micdm.ftr.async.DownloadThemePageTask;

import java.util.Date;
import java.util.HashMap;

import android.text.format.DateUtils;
import android.util.Log;

/**
 * Класс темы.
 * @author Mic, 2011
 *
 */
public class Theme {

	public interface OnPageLoadedCommand {
		public void callback(ThemePage page);
	}
	
	interface OnPageCountLoadedCommand {
		public void callback(Integer pageCount);
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
		return _pageCount;
	}
	
	/**
	 * Загружает количество страниц.
	 */
	protected void _loadPageCount(final OnPageCountLoadedCommand onLoaded) {
		Log.d(toString(), "loading page count");
		DownloadThemePageTask task = new DownloadThemePageTask(this, 0) {
			@Override
			public void onPostExecute(Result result) {
				_pageCount = result.getPageCount();
				Log.d(toString(), "page count loaded: " + _pageCount);
				if (_pageCount == 1) {
					_pages.put(0, result.getPage());
				}
				onLoaded.callback(_pageCount);
			}
		};
		task.execute();
	}
	
	/**
	 * Загружает страницу с указанным номером.
	 */
	protected void _loadPage(final Integer pageNumber, final OnPageLoadedCommand onLoaded) {
		Log.d(toString(), "loading page #" + pageNumber);
		DownloadThemePageTask task = new DownloadThemePageTask(this, pageNumber) {
			@Override
			public void onPostExecute(Result result) {
				Log.d(toString(), "page #" + pageNumber + " loaded");
				onLoaded.callback(result.getPage());
			}
		};
		task.execute();
	}
	
	/**
	 * Загружает одну страницу и делает обратный вызов.
	 */
	public void loadPage(final Integer pageNumber, final OnPageLoadedCommand onLoaded) {
		if (_pageCount == null) {
			_loadPageCount(new OnPageCountLoadedCommand() {
				@Override
				public void callback(Integer pageCount) {
					if (pageCount == 1) {
						onLoaded.callback(_pages.get(0));
					} else {
						_loadPage(pageNumber, onLoaded);
					}
				}
			});
		} else {
			_loadPage(pageNumber, onLoaded);
		}
	}
}
