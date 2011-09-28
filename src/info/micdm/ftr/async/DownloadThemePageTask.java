package info.micdm.ftr.async;

import info.micdm.ftr.Message;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;
import info.micdm.ftr.utils.DateParser;

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
class ThemeParser {

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
	 * Возвращает тело сообщения.
	 */
	protected String _getMessageBody(String rawText) {
		return rawText.replace("\n", "").replaceAll("<br />|<br>", "\n").trim();
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
			Integer id = new Integer(matcher.group(3));
			Date created = DateParser.parse(matcher.group(1).trim(), DateParser.Type.MESSAGE);
			String body = _getMessageBody(matcher.group(4));
			messages.add(0, new Message(id, created, matcher.group(2).trim(), body));
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

	public interface OnLoadCommand {
		public void callback(ThemePage page);
	}
	
	/**
	 * Тема, из которой загружаем.
	 */
	protected Theme _theme;
	
	/**
	 * Номер загружаемой страницы.
	 */
	protected Integer _pageNumber;
	
	/**
	 * Будет выполнено, когда загрузка закончится.
	 */
	protected OnLoadCommand _onLoad;
	
	public DownloadThemePageTask(Theme theme, Integer pageNumber, OnLoadCommand onLoad) {
		_theme = theme;
		_pageNumber = pageNumber;
		_onLoad = onLoad;
	}
	
	/**
	 * Возвращает адрес страницы.
	 */
	@Override
	protected String _getUri() {
		return "http://forum.tomsk.ru/forum/12/1293428/?p=100";
		//return "http://mic-dm.tom.ru/theme.html";
//		String url = "http://forum.tomsk.ru/forum/" + _theme.getGroupId() + "/" + _theme.getId();
//		return url + "/?p=" + (_theme.getPageCount() - _pageNumber);
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
	
	@Override
	protected void onPostExecute(ThemePage page) {
		_onLoad.callback(page);
	}
}
