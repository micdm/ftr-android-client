package info.micdm.ftr.async;

import info.micdm.ftr.Theme;
import info.micdm.ftr.utils.DateParser;
import info.micdm.ftr.utils.HtmlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * Парсер страницы со списком тем.
 * @author Mic, 2011
 *
 */
class GroupParser extends HtmlParser {
	
	/**
	 * Возвращает регулярное выражение для разбора.
	 */
	protected String _getPattern() {
		// Дата последнего сообщения в теме:
		String result = "<td class=\"tdw1\">([^<]+)</td>\\s+";
		// Идентификатор группы, идентификатор темы, название темы:
		result += "<td class=\"tdw3\"><a href=\"/forum/(\\d+)/(\\d+)/\"[^>]*>([^<]+).+?";
		// Автор темы:
		return result + "<td class=\"tdw2\">(?:\\s+<a[^>]+>)?([^<]+)";
	}
	
	/**
	 * Создает тему.
	 */
	protected Theme _getTheme(Matcher matcher) {
		Integer groupId = new Integer(matcher.group(2));
		Integer id = new Integer(matcher.group(3));
		Date updated = _getCorrectDate(DateParser.parse(matcher.group(1), DateParser.Type.THEME));
		String author = _normalizeString(matcher.group(5));
		String title = _normalizeString(matcher.group(4));
		return new Theme(groupId, id, updated, author, title);
	}
	
	/**
	 * Парсит текст в список тем.
	 */
	public ArrayList<Theme> parse(String text) {
		Log.d(toString(), "parsing group page");
		ArrayList<Theme> themes = new ArrayList<Theme>();
		Pattern pattern = Pattern.compile(_getPattern(), Pattern.DOTALL);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			themes.add(_getTheme(matcher));
		}
		Log.d(toString(), "group page parsed");
		return themes;
	}
}

/**
 * Фоновый загрузчик страницы со списком тем.
 * @author Mic, 2011
 * 
 */
public class DownloadGroupPageTask extends DownloadTask<Void, Void, ArrayList<Theme>> {

	/**
	 * Идентификатор группы, которую будем загружать.
	 */
	protected Integer _groupId;

	public DownloadGroupPageTask(Integer groupId) {
		_groupId = groupId;
	}
	
	/**
	 * Возвращает адрес страницы.
	 */
	@Override
	protected String _getUri() {
		String result = "http://forum.tomsk.ru/forum/";
		if (_groupId != 0) {
			result += _groupId;
		}
		return result;
	}

	@Override
	public ArrayList<Theme> doInBackground(Void... voids) {
		try {
			String body = _downloadPage();
			return new GroupParser().parse(body);
		} catch (IOException e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}
}
