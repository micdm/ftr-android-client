package info.micdm.ftr;

import info.micdm.ftr.async.DownloadTask;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	protected ProgressDialog _dialog;

	/**
	 * Отображает список тем.
	 */
	protected void _showThemes(ThemeGroup group) {
		ArrayAdapter<Theme> adapter = new ArrayAdapter<Theme>(this, R.layout.list_item, group.getThemes());
		android.widget.ListView list = (ListView)findViewById(R.id.themes);
		list.setAdapter(adapter);
	}
	
	/**
	 * Отображает группу тем.
	 * Если группа еще не загружена, загружает.
	 */
	protected void _showThemeGroup() {
		ThemeGroups groups = ThemeGroups.getInstance();
		ThemeGroup group = groups.getGroup(0);
		if (group == null) {
			Log.d(toString(), "group not found, loading");
			_dialog = ProgressDialog.show(this, "Loading", "Loading group, please wait");
			ThemeGroup.load(0, new ThemeGroup.Command() {
				public void callback(ThemeGroup group) {
					ThemeGroups.getInstance().addGroup(group);
					_showThemes(group);
					_dialog.dismiss();
				}
			});
		} else {
			Log.d(toString(), "group found");
			_showThemes(group);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		_showThemeGroup();
	}
}
