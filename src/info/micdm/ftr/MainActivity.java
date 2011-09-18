package info.micdm.ftr;

import info.micdm.ftr.async.DownloadTask;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	protected ProgressDialog _dialog;
	
	protected void _onThemesAvailable(ArrayList<String> themes) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, themes);
		android.widget.ListView list = (ListView)findViewById(R.id.themes);
		list.setAdapter(adapter);
	}
	
	protected void _getThemes() {
		_dialog = ProgressDialog.show(this, "Loading", "Loading the page, please wait");
		DownloadTask task = new DownloadTask(new DownloadTask.Command() {
			public void callback(ArrayList<String> themes) {
				_dialog.dismiss();
				_onThemesAvailable(themes);
			}
		});
		task.execute("");		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		_getThemes();
	}
}
