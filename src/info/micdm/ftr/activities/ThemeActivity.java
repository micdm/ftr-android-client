package info.micdm.ftr.activities;

import java.util.ArrayList;

import info.micdm.ftr.Message;
import info.micdm.ftr.R;
import info.micdm.ftr.Theme;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ThemeActivity extends Activity {

	/**
	 * Вызывается, когда будет доступен список тем.
	 */
	protected void _onThemeLoaded(Theme theme) {
		ArrayAdapter<Message> adapter = new ArrayAdapter<Message>(this, R.layout.list_item, theme.getMessages());
		ListView list = (ListView)findViewById(R.id.themes);
		list.setAdapter(adapter);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
		
		final Theme theme = new Theme(15, 1319789, "Дарю идею для кафе");
		theme.load(new Theme.Command() {
			public void callback() {
				_onThemeLoaded(theme);
			}
		});
	}
}
