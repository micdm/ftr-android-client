package info.micdm.ftr.async;

import info.micdm.ftr.Message;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;
import info.micdm.ftr.utils.DateParser;
import info.micdm.ftr.utils.HtmlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.MatchResult;

import info.micdm.ftr.utils.Log;

/**
 * Парсер информации о количестве страниц.
 * @author Mic, 2011
 *
 */
class PageCountParser extends HtmlParser {
	
	/**
	 * Результат поиска.
	 */
	protected Integer _pageCount;
	
	@Override
	protected String _getPattern() {
		return "<div class=\"paging\".+из (\\d+)";
	}
	
	@Override
	protected Boolean _createItem(MatchResult match) {
		_pageCount = new Integer(match.group(1));
		return false;
	}
	
	/**
	 * Выкусывает количество страниц из строки.
	 */
	public Integer parse(String text) {
		_findMatches(text);
		return _pageCount;
	}
}

/**
 * Парсер сообщений.
 * @author Mic, 2011
 *
 */
class MessageParser extends HtmlParser {

	/**
	 * Результат поиска.
	 */
	protected ArrayList<Message> _messages = new ArrayList<Message>();
	
	@Override
	protected String _getPattern() {
		// Дата добавления:
		String result = "<div class=\"box_user\">([^|]+).+?";
		// Автор:
		result += "<(?:span class=\"name1\"|a.*?class=\"name\".*?)>([^<]+).+?";
		// Заголовок и тело:
		return result + "<div id=\"message_(\\d+)\" class=\"text_box_2_mess\">(.+?)</div>\\s+<a href=\"#ftop";
	}
	
	@Override
	protected Boolean _createItem(MatchResult match) {
		Integer id = new Integer(match.group(3));
		Date created = _getCorrectDate(DateParser.parse(match.group(1).trim(), DateParser.Type.MESSAGE));
		String author = _normalizeString(match.group(2));
		String body = _normalizeString(match.group(4).replace("\n", "").replaceAll("<br />|<br>", "\n"));
		_messages.add(new Message(id, created, author, body));
		return true;
	}
	
	/**
	 * Возвращает данные страницы (сообщения, ...).
	 */
	protected ThemePage parse(String text) {
		_findMatches(text);
		return new ThemePage(_messages);
	}
}

/**
 * Класс для фоновой загрузки одной страницы.
 * @author Mic, 2011
 * 
 */
public class DownloadThemePageTask extends DownloadTask<Void, Void, DownloadThemePageTask.Result> {

	/**
	 * Результат загрузки.
	 * @author Mic, 2011
	 *
	 */
	public class Result {
		
		/**
		 * Количество страниц в теме.
		 */
		protected Integer _pageCount;
		
		/**
		 * Страница с сообщениями.
		 */
		protected ThemePage _page;
		
		public Result(Integer pageCount, ThemePage page) {
			_pageCount = pageCount;
			_page = page;
		}
		
		/**
		 * Возвращает количество страниц в теме.
		 */
		public Integer getPageCount() {
			return _pageCount;
		}
		
		/**
		 * Возвращает страницу с сообщениями.
		 */
		public ThemePage getPage() {
			return _page;
		}
	}
	
	/**
	 * Тема, из которой загружаем.
	 */
	protected Theme _theme;
	
	/**
	 * Номер загружаемой страницы.
	 */
	protected Integer _pageNumber;
	
	public DownloadThemePageTask(Theme theme, Integer pageNumber) {
		_theme = theme;
		_pageNumber = pageNumber;
	}
	
	/**
	 * Возвращает адрес страницы.
	 */
	@Override
	protected String _getUrn() {
		String result = "/" + _theme.getGroupId() + "/" + _theme.getId() + "/";
		Integer pageCount = _theme.getPageCount();
		if (pageCount != null) {
			return result + "?p=" + (pageCount - _pageNumber);
		}
		return result;
	}

	@Override
	public Result doInBackground(Void... voids) {
		try {
			String body = _downloadPage();
			Integer pageCount = new PageCountParser().parse(body);
			ThemePage page = new MessageParser().parse(body);
			return new Result(pageCount, page);
		} catch (IOException e) {
			Log.error("error during loading theme", e);
			return null;
		}
	}
}
