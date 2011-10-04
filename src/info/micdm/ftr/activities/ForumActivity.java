package info.micdm.ftr.activities;

import info.micdm.ftr.Forum;
import info.micdm.ftr.Group;
import info.micdm.ftr.R;
import info.micdm.ftr.utils.Log;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Экран с группами тем.
 * @author Mic, 2011
 *
 */
public class ForumActivity extends ListActivity {

	/**
	 * Заполняет список групп.
	 */
	protected void _setData() {
		ArrayList<Group> groups = Forum.INSTANCE.getAllGroups();
		ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(this, R.layout.group_list_item, groups);
		getListView().setAdapter(adapter);
	}
	
	/**
	 * Выполняется, когда пользователь выбрал определенную группу.
	 */
	protected void _onGroupSelected(Group group) {
		Log.debug("group \"" + group.getTitle() + "\" (" + group.getId() + ") selected");
		Intent intent = new Intent(this, GroupActivity.class);
		intent.putExtra("groupId", group.getId());
		startActivity(intent);
	}
	
	/**
	 * Слушает клик по элементу списка, чтобы перейти в соответствующую группу.
	 */
	protected void _listenForClick() {
		getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				Group group = (Group)adapter.getItemAtPosition(position);
				_onGroupSelected(group);
			}
		});
	}
	
	/**
	 * Отображает список групп.
	 */
	protected void _showGroups() {
		_setData();
		_listenForClick();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_showGroups();
		//_onGroupSelected(Forum.INSTANCE.getGroup(0));
	}
}
