package info.micdm.ftr.async;

import info.micdm.ftr.Message;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Парсер сообщений.
 * @author Mic, 2011
 *
 */
class MessageParser {
	
	/**
	 * Будет разбирать даты сообщений.
	 */
	protected SimpleDateFormat _dateFormat;
	
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
	 * Возвращает дату сообщения.
	 */
	protected Date _getMessageDate(String rawText) {
		if (_dateFormat == null) {
			_dateFormat = new SimpleDateFormat("dd.MM.yyyy (kk:mm)");
			_dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Novosibirsk"));
		}
		try {
			return _dateFormat.parse(rawText.trim());
		} catch (ParseException e) {
			Log.w(toString(), "can not parse message date: " + rawText);
			return null;
		}
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
		Log.d(toString(), "parsing page");
		ArrayList<Message> messages = new ArrayList<Message>();
		Pattern pattern = Pattern.compile(_getPattern(), Pattern.DOTALL);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			Integer id = new Integer(matcher.group(3));
			Date created = _getMessageDate(matcher.group(1));
			String body = _getMessageBody(matcher.group(4));
			messages.add(0, new Message(id, created, matcher.group(2).trim(), body));
		}
		Log.d(toString(), "page parsed");
		return new ThemePage(messages);
	}
}

/**
 * Класс для фоновой загрузки одной страницы.
 * @author Mic, 2011
 * 
 */
public class DownloadThemePageTask extends AsyncTask<Void, Void, ThemePage> {

	public interface Command {
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
	protected Command _command;
	
	public DownloadThemePageTask(Theme theme, Integer pageNumber, Command command) {
		_theme = theme;
		_pageNumber = pageNumber;
		_command = command;
	}
	
	/**
	 * Возвращает адрес страницы.
	 */
	protected String _getUri() {
		return "http://mic-dm.tom.ru/theme.html";
//		String url = "http://forum.tomsk.ru/forum/" + _theme.getGroupId() + "/" + _theme.getId();
//		return url + "/?p=" + (_theme.getPageCount() - _pageNumber);
	}

	@Override
	public ThemePage doInBackground(Void... voids) {
		try {
			Log.d(toString(), "loading page " + _pageNumber + " of theme " + _theme.getId());
			AndroidHttpClient client = AndroidHttpClient.newInstance("Android FTR Client");
			HttpGet request = new HttpGet(_getUri());
			BasicHttpResponse response = (BasicHttpResponse)client.execute(request);
			String body = EntityUtils.toString(response.getEntity(), "utf8");
			client.close();
			Log.d(toString(), "page loaded");
			return new MessageParser().parse(body);
		} catch (Exception e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(ThemePage page) {
		_command.callback(page);
	}
}
