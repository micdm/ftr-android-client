package info.micdm.ftr.activities;

import info.micdm.ftr.Forum;
import info.micdm.ftr.R;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;
import info.micdm.ftr.adapters.ThemeAdapter;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Экран со списком сообщений.
 * @author Mic, 2011
 *
 */
public class ThemeActivity extends Activity {
	
	/**
	 * Определяет тему, которую надо загрузить.
	 */
	protected Theme _getTheme() {
		Bundle extras = getIntent().getExtras();
		Integer groupId = extras.getInt("groupId");
		Integer themeId = extras.getInt("themeId");
		return Forum.INSTANCE.getGroup(groupId).getTheme(themeId);
	}
	
	/**
	 * Вызывается, когда загрузилась очередная страница.
	 */
	protected void _onPageLoaded(ThemePage page) {
		ThemeAdapter adapter = new ThemeAdapter(this, page.getMessages());
		getListView().setAdapter(adapter);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme);

		Theme theme = _getTheme();
		
		TextView title = (TextView)findViewById(R.id.themeTitle);
		title.setText(theme.getTitle());
		
		theme.loadPage(_taskManager, 0, new Theme.OnPageLoadedCommand() {
			@Override
			public void callback(ThemePage page) {
				_onPageLoaded(page);
			}
		});
	}
}
