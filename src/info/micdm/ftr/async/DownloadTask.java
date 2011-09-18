package info.micdm.ftr.async;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.os.AsyncTask;
import android.util.Log;

public class DownloadTask extends AsyncTask<String, Void, ArrayList<String>> {

	public interface Command {
		public void callback(ArrayList<String> themes);
	}

	protected Command _command;

	public DownloadTask(Command command) {
		_command = command;
	}

	public ArrayList<String> doInBackground(String... urls) {
		try {
			Connection connection = Jsoup.connect("http://forum.tomsk.ru/" + urls[0]);
			Document doc = connection.get();
			ArrayList<String> themes = new ArrayList<String>();
			for (Element element : doc.select(".themes .tdw3 a")) {
				themes.add(element.text());
			}
			return themes;
		} catch (IOException e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}

	public void onPostExecute(ArrayList<String> themes) {
		_command.callback(themes);
	}
}
