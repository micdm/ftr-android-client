package info.micdm.ftr.async;

import info.micdm.ftr.Message;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
	
	/**
	 * Возвращает тело сообщения.
	 */
	protected String _getMessageBody(String rawHtml) {
		return rawHtml.replace("\n", "").replace("<br />", "\n");
	}
	
	/**
	 * Возвращает данные страницы (сообщения, ...).
	 */
	protected ThemePage _getData(String html) {
		ArrayList<Message> messages = new ArrayList<Message>();
		Pattern pattern = Pattern.compile("<div id=\"message_(\\d+)\" class=\"text_box_2_mess\">(.+?)</div>\\s+<a href=\"#ftop", Pattern.DOTALL);
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			Integer id = new Integer(matcher.group(1));
			String body = _getMessageBody(matcher.group(2));
			messages.add(0, new Message(id, "", body));
		}
		return new ThemePage(messages);
	}
	
	@Override
	public ThemePage doInBackground(Void... voids) {
		try {
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
