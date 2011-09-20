package info.micdm.ftr.async;

import info.micdm.ftr.Message;
import info.micdm.ftr.Theme;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadThemeTask extends AsyncTask<Void, Void, Theme> {

	public interface Command {
		public void callback(Theme theme);
	}
	
	protected Theme _theme;
	
	protected Command _command;
	
	public DownloadThemeTask(Theme theme, Command command) {
		_theme = theme;
		_command = command;
	}
	
	protected String _getUrl() {
		return "http://forum.tomsk.ru/forum/" + _theme.getGroupId() + "/" + _theme.getId();
	}
	
	protected Integer _getPageCount(Document doc) {
		return new Integer(doc.select("form.paging_sel").first().ownText().replace("из", "").trim());
	}
	
	protected Message _getMessage(Element element) {
		return new Message("", element.child(0).text());
	}
	
	protected ArrayList<Message> _getMessages(Document doc) {
		ArrayList<Message> messages = new ArrayList<Message>();
		for (Element element : doc.select("div.text_box_2")) {
			messages.add(_getMessage(element));
		}
		return messages;
	}
	
	protected Theme _getThemeData(Document doc) {
		Integer pageCount = _getPageCount(doc);
		ArrayList<Message> messages = _getMessages(doc);
		return new Theme(pageCount, messages);
	}
	
	@Override
	public Theme doInBackground(Void... voids) {
		try {
			String url = _getUrl();
			Connection connection = Jsoup.connect(url);
			Document doc = connection.get();
			return _getThemeData(doc);
		} catch (IOException e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}
	
	protected void onPostExecute(Theme theme) {
		_command.callback(theme);
	}
}
