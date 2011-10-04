package info.micdm.ftr.activities;

import info.micdm.ftr.Forum;
import info.micdm.ftr.R;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;
import info.micdm.ftr.adapters.ThemeAdapter;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Экран со списком сообщений.
 * @author Mic, 2011
 *
 */
public class ThemeActivity extends ListActivity {

	/**
	 * Вызывается, когда загрузилась очередная страница.
	 */
	protected void _onPageLoaded(ThemePage page) {
		ThemeAdapter adapter = new ThemeAdapter(this, page.getMessages());
		getListView().setAdapter(adapter);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO: добавить прогресс-диалог при загрузке + прогрессбар при подгрузке страниц (?)
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme);

		Bundle extras = getIntent().getExtras();
		Integer groupId = extras.getInt("groupId");
		Integer themeId = extras.getInt("themeId");
		
		Theme theme = Forum.INSTANCE.getGroup(groupId).getTheme(themeId);
		
		TextView title = (TextView)findViewById(R.id.themeTitle);
		title.setText(theme.getTitle());
		
		theme.loadPage(0, new Theme.OnPageLoadedCommand() {
			@Override
			public void callback(ThemePage page) {
				_onPageLoaded(page);
			}
		});
	}
}
