package info.micdm.ftr.async;

import info.micdm.ftr.Message;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;

import java.util.ArrayList;

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
	 * Возвращает данные страницы (сообщения, ...).
	 */
	protected ThemePage _getData(Document doc) {
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Element element : doc.select("div.text_box_2_mess")) {
			Integer id = new Integer(element.attr("id").replace("message_", ""));
			String body = element.text();
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
			String body = response.body();
			Integer start = body.indexOf("<div class=\"paging\">");
			Integer end = body.lastIndexOf("<div class=\"paging\">");
			Document doc = Jsoup.parse(body.substring(start, end));
			return _getData(doc);
		} catch (Exception e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}
	
	protected void onPostExecute(ThemePage page) {
		_command.callback(page);
	}
}
