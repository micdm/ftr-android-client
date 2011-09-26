package info.micdm.ftr.activities;

import info.micdm.ftr.Group;
import info.micdm.ftr.Groups;
import info.micdm.ftr.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Экран с группами тем.
 * @author Mic, 2011
 *
 */
public class GroupsActivity extends Activity {

	/**
	 * Заполняет список групп.
	 */
	protected void _setData(ListView list) {
		ArrayList<Group> groups = Groups.getInstance().getAllGroups();
		ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this, R.layout.list_item, groups);
		list.setAdapter(adapter);
	}
	
	protected void _onGroupSelected(Group group) {
		Log.d(toString(), "group \"" + group.getTitle() + "\" (" + group.getId() + ") selected");
		Intent intent = new Intent(this, GroupActivity.class);
		intent.putExtra("groupId", group.getId());
		startActivity(intent);
	}
	
	/**
	 * Слушает клик по элементу списка, чтобы перейти в соответствующую группу.
	 */
	protected void _listenForClick(final ListView list) {
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				Group group = (Group)list.getItemAtPosition(position);
				_onGroupSelected(group);
			}
		});
	}
	
	/**
	 * Отображает список групп.
	 */
	protected void _showGroups() {
		ListView list = (ListView)findViewById(R.id.groups);
		_setData(list);
		_listenForClick(list);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.groups);
		_showGroups();
	}
}
