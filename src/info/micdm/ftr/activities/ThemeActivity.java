package info.micdm.ftr.activities;

import info.micdm.ftr.Message;
import info.micdm.ftr.R;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ThemeActivity extends Activity {

	protected ArrayAdapter<Message> _adapter;
	
	protected void _onPageLoaded(ThemePage page) {
		Log.d(toString(), page.getMessages().get(0).toString());
		Log.d(toString(), page.getMessages().get(page.getMessages().size() - 1).toString());
		for (Message message : page.getMessages()) {
			_adapter.add(message);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme);
		
		ListView list = (ListView)findViewById(R.id.messages);
		_adapter = new ArrayAdapter<Message>(this, R.layout.list_item);
		list.setAdapter(_adapter);
		
		final Theme theme = new Theme(4, 1323287, "");
		theme.loadPage(0, new Theme.OnPageLoadedCommand() {
			public void callback(ThemePage page) {
				_onPageLoaded(page);

				theme.loadPage(1, new Theme.OnPageLoadedCommand() {
					public void callback(ThemePage page) {
						_onPageLoaded(page);
					}
				});
			}
		});
	}
}
