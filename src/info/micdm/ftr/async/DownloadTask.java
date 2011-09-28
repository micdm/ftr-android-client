package info.micdm.ftr.async;

import info.micdm.ftr.Theme;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import org.w3c.dom.Document;

import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author Mic, 2011
 * 
 * TODO распарсить список тем регулярками
 */
public class DownloadTask extends AsyncTask<String, Void, ArrayList<Theme>> {

	public interface Command {
		public void callback(ArrayList<Theme> themes);
	}

	protected Command _command;

	public DownloadTask(Command command) {
		_command = command;
	}
	
//	protected ArrayList<Theme> _elementsToThemes(Elements elements) {
//		ArrayList<Theme> themes = new ArrayList<Theme>();
//		for (Element element : elements) {
//			Theme theme = new Theme(0, 0, element.text()); 
//			themes.add(theme);
//		}
//		return themes;
//	}

//	@Override
//	public ArrayList<Theme> doInBackground(String... urls) {
//		try {
//			Connection connection = Jsoup.connect("http://forum.tomsk.ru/" + urls[0]);
//			Document doc = connection.get();
//			return _elementsToThemes(doc.select(".themes .tdw3 a"));
//		} catch (IOException e) {
//			Log.e(toString(), e.toString());
//			return null;
//		}
//	}
	
	@Override
	public ArrayList<Theme> doInBackground(String... urls) {
		return null;
	}

	@Override
	public void onPostExecute(ArrayList<Theme> themes) {
		_command.callback(themes);
	}
}
