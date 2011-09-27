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

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import android.os.AsyncTask;
import android.util.Log;

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
	
	/**
	 * Будет разбирать даты сообщений.
	 */
	protected SimpleDateFormat _dateFormat;
	
	public DownloadThemePageTask(Theme theme, Integer pageNumber, Command command) {
		_theme = theme;
		_pageNumber = pageNumber;
		_command = command;
	}
	
	/**
	 * Возвращает адрес страницы.
	 */
	protected String _getUrl() {
		String url = "http://forum.tomsk.ru/forum/" + _theme.getGroupId() + "/" + _theme.getId();
		return url + "/?p=" + (_theme.getPageCount() - _pageNumber);
	}
	
	protected String _getPattern() {
		String result = "";
		result += "<div class=\"box_user\">([^|]+).+?";
		result += "<(?:span class=\"name1\"|a.*?class=\"name\".*?)>([^<]+).+?";
		result += "<div id=\"message_(\\d+)\" class=\"text_box_2_mess\">(.+?)</div>\\s+<a href=\"#ftop";
		return result;
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
		return rawText.replace("\n", "").replace("<br />", "\n").trim();
	}
	
	/**
	 * Возвращает данные страницы (сообщения, ...).
	 */
	protected ThemePage _getData(String html) {
		ArrayList<Message> messages = new ArrayList<Message>();
		Pattern pattern = Pattern.compile(_getPattern(), Pattern.DOTALL);
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			Integer id = new Integer(matcher.group(3));
			Date created = _getMessageDate(matcher.group(1));
			String body = _getMessageBody(matcher.group(4));
			messages.add(0, new Message(id, created, matcher.group(2).trim(), body));
		}
		return new ThemePage(messages);
	}
	
	@Override
	public ThemePage doInBackground(Void... voids) {
		try {
			Log.d(toString(), "loading page " + _pageNumber + " of theme " + _theme.getId());
			String url = _getUrl();
			Connection connection = Jsoup.connect(url);
			Response response = connection.execute();
			return _getData(response.body());
		} catch (Exception e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}
	
	protected void onPostExecute(ThemePage page) {
		_command.callback(page);
	}
}
