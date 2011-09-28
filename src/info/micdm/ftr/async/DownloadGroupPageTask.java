package info.micdm.ftr.async;

import info.micdm.ftr.Theme;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Парсер страницы со списком тем.
 * @author Mic, 2011
 *
 */
class GroupParser {
	
	/**
	 * Возвращает регулярное выражение для разбора.
	 */
	protected String _getPattern() {
		// Дата последнего сообщения в теме:
		String result = "<td class=\"tdw1\">([^<]+)</td>\\s+";
		// Идентификатор группы, идентификатор темы, название темы:
		result += "<td class=\"tdw3\"><a href=\"/forum/(\\d+)/(\\d+)/\"[^>]+>([^<]+).+?";
		// Автор темы:
		return result + "<td class=\"tdw2\">(?:\\s+<a[^>]+>)?([^<]+)<";
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
			Integer groupId = new Integer(matcher.group(2));
			Integer id = new Integer(matcher.group(3));
			themes.add(new Theme(groupId, id, matcher.group(4)));
		}
		Log.d(toString(), "group page parsed");
		return themes;
	}
}

/**
 * Фоновый загрузчик страницы со списком тем.
 * @author Mic, 2011
 * 
 * TODO объединить с загрузчиком сообщений
 */
public class DownloadGroupPageTask extends AsyncTask<Void, Void, ArrayList<Theme>> {

	public interface OnLoadCommand {
		public void callback(ArrayList<Theme> themes);
	}

	/**
	 * Выполнится по окончании загрузки.
	 */
	protected OnLoadCommand _onLoad;

	public DownloadGroupPageTask(OnLoadCommand onLoad) {
		_onLoad = onLoad;
	}
	
	/**
	 * Возвращает адрес страницы.
	 */
	protected String _getUri() {
		return "http://mic-dm.tom.ru/hot.html";
	}

	@Override
	public ArrayList<Theme> doInBackground(Void... voids) {
		try {
			AndroidHttpClient client = AndroidHttpClient.newInstance("Android FTR Client");
			HttpGet request = new HttpGet(_getUri());
			BasicHttpResponse response = (BasicHttpResponse)client.execute(request);
			String body = EntityUtils.toString(response.getEntity(), "utf8");
			client.close();
			return new GroupParser().parse(body);
		} catch (Exception e) {
			Log.e(toString(), e.toString());
			return null;
		}
	}

	@Override
	public void onPostExecute(ArrayList<Theme> themes) {
		_onLoad.callback(themes);
	}
}
