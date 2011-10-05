package info.micdm.ftr.async;

import info.micdm.ftr.utils.Log;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;

import android.net.http.AndroidHttpClient;

/**
 * Абстрактный загрузчик страниц.
 * @author Mic, 2011
 *
 */
public abstract class DownloadTask<Progress, Result> extends Task<Progress, Result> {

	/**
	 * Возвращает адрес страницы для скачивания.
	 */
	protected abstract String _getUrn();
	
	/**
	 * Скачивает страницу и возвращает ее тело.
	 */
	protected String _downloadPage() throws IOException {
		String uri = "http://forum.tomsk.ru/forum" + _getUrn();
		Log.debug("downloading page " + uri);
		AndroidHttpClient client = AndroidHttpClient.newInstance("Android FTR Client");
		HttpGet request = new HttpGet(uri);
		BasicHttpResponse response = (BasicHttpResponse)client.execute(request);
		String body = EntityUtils.toString(response.getEntity(), "utf8");
		client.close();
		Log.debug("page loaded");
		return body;
	}
}
