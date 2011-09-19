package info.micdm.ftr.activities;

import info.micdm.ftr.R;
import info.micdm.ftr.ThemeGroup;
import info.micdm.ftr.ThemeGroups;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GroupsActivity extends Activity {

	/**
	 * Отображает список групп.
	 */
	protected void _showGroups() {
		ArrayList<ThemeGroup> groups = ThemeGroups.getInstance().getAllGroups();
		ArrayAdapter<ThemeGroup> adapter = new ArrayAdapter<ThemeGroup>(this, R.layout.list_item, groups);
		android.widget.ListView list = (ListView)findViewById(R.id.groups);
		list.setAdapter(adapter);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groups);
		_showGroups();
	}
}
