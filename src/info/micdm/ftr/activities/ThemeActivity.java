package info.micdm.ftr.activities;

import info.micdm.ftr.Forum;
import info.micdm.ftr.R;
import info.micdm.ftr.Theme;
import info.micdm.ftr.ThemePage;
import info.micdm.ftr.adapters.ThemeAdapter;
import info.micdm.ftr.utils.Log;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.TextView;

/**
 * Экран со списком сообщений.
 * @author Mic, 2011
 *
 */
public class ThemeActivity extends TaskActivity {
	
	/**
	 * Адаптер для хранения сообщений.
	 */
	protected ThemeAdapter _adapter;
	
	/**
	 * Тема, которую отображаем.
	 */
	protected Theme _theme;
	
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
		if (_adapter == null) {
			_adapter = new ThemeAdapter(this);
			getListView().setAdapter(_adapter);
		}
		_adapter.addItems(page.getMessages());
	}
	
	/**
	 * Слушает событие прокрутки списка.
	 */
	protected void _listenForScroll() {
		getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				Log.debug("scrolled to " + firstVisibleItem);
				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
					_theme.loadNextPage(_taskManager, new Theme.OnPageLoadedCommand() {
						@Override
						public void callback(ThemePage page) {
							_onPageLoaded(page);
						}
					});
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
		});
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.theme);

		_theme = _getTheme();
		
		TextView title = (TextView)findViewById(R.id.themeTitle);
		title.setText(_theme.getTitle());
		
		_theme.loadFirstPage(_taskManager, new Theme.OnPageLoadedCommand() {
			@Override
			public void callback(ThemePage page) {
				_onPageLoaded(page);
				_listenForScroll();
			}
		});
	}
}
