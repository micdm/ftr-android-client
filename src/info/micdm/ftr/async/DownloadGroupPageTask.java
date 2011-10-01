package info.micdm.ftr.async;

import info.micdm.ftr.Theme;
import info.micdm.ftr.utils.DateParser;
import info.micdm.ftr.utils.HtmlParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.MatchResult;

import android.util.Log;

/**
 * Парсер страницы со списком тем.
 * @author Mic, 2011
 *
 */
class GroupParser extends HtmlParser {
	
	protected ArrayList<Theme> _themes = new ArrayList<Theme>();
	
	@Override
	protected String _getPattern() {
		// Дата последнего сообщения в теме:
		String result = "<td class=\"tdw1\">([^<]+)</td>\\s+";
		// Идентификатор группы, идентификатор темы, название темы:
		result += "<td class=\"tdw3\"><a href=\"/forum/(\\d+)/(\\d+)/\"[^>]*>([^<]+).+?";
		// Автор темы:
		return result + "<td class=\"tdw2\">(?:\\s+<a[^>]+>)?([^<]+)";
	}
	
	@Override
	protected Boolean _createItem(MatchResult match) {
		Integer groupId = new Integer(match.group(2));
		Integer id = new Integer(match.group(3));
		Date updated = _getCorrectDate(DateParser.parse(match.group(1), DateParser.Type.THEME));
		String author = _normalizeString(match.group(5));
		String title = _normalizeString(match.group(4));
		_themes.add(new Theme(groupId, id, updated, author, title));
		return true;
	}
	
	/**
	 * Парсит текст в список тем.
	 */
	public ArrayList<Theme> parse(String text) {
		_findMatches(text);
		return _themes;
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
	protected String _getUrn() {
		if (_groupId == 0) {
			return "/";
		}
		return "/" + _groupId + "/";
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
