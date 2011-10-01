package info.micdm.ftr.async;

import info.micdm.ftr.Message;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;
import info.micdm.ftr.utils.DateParser;
import info.micdm.ftr.utils.HtmlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * Парсер сообщений.
 * @author Mic, 2011
 *
 */
class ThemeParser extends HtmlParser {

	/**
	 * Возвращает шаблон для поиска.
	 */
	protected String _getPattern() {
		// Дата добавления:
		String result = "<div class=\"box_user\">([^|]+).+?";
		// Автор:
		result += "<(?:span class=\"name1\"|a.*?class=\"name\".*?)>([^<]+).+?";
		// Заголовок и тело:
		return result + "<div id=\"message_(\\d+)\" class=\"text_box_2_mess\">(.+?)</div>\\s+<a href=\"#ftop";
	}
	
	/**
	 * Создает сообщение.
	 */
	protected Message _getMessage(Matcher matcher) {
		Integer id = new Integer(matcher.group(3));
		Date created = _getCorrectDate(DateParser.parse(matcher.group(1).trim(), DateParser.Type.MESSAGE));
		String author = _normalizeString(matcher.group(2));
		String body = _normalizeString(matcher.group(4).replace("\n", "").replaceAll("<br />|<br>", "\n"));
		return new Message(id, created, author, body);
	}
	
	/**
	 * Возвращает данные страницы (сообщения, ...).
	 */
	protected ThemePage parse(String text) {
		Log.d(toString(), "parsing theme page");
		ArrayList<Message> messages = new ArrayList<Message>();
		Pattern pattern = Pattern.compile(_getPattern(), Pattern.DOTALL);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			messages.add(0, _getMessage(matcher));
		}
		Log.d(toString(), "theme page parsed");
		return new ThemePage(messages);
	}
}

/**
 * Класс для фоновой загрузки одной страницы.
 * @author Mic, 2011
 * 
 */
public class DownloadThemePageTask extends DownloadTask<Void, Void, ThemePage> {

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
		return "/12/1293428/?p=100";
	}

	@Override
	public ThemePage doInBackground(Void... voids) {
		try {
			String body = _downloadPage();
			return new ThemeParser().parse(body);
		} catch (IOException e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}
}
