package info.micdm.ftr;

import info.micdm.ftr.async.DownloadThemeTask;

import java.util.ArrayList;

public class Theme {

	public interface Command {
		public void callback();
	}
	
	protected Integer _groupId;
	
	protected Integer _id;
	
	protected String _title;
	
	protected Boolean _isLoaded = false;
	
	protected Integer _pageCount;
	
	protected ArrayList<Message> _messages;
	
	public Theme(Integer groupId, Integer id, String title) {
		_groupId = groupId;
		_id = id;
		_title = title;
	}

	public Theme(Integer pageCount, ArrayList<Message> messages) {
		_pageCount = pageCount;
		_messages = messages;
	}
	
	@Override
	public String toString() {
		return _title;
	}
	
	public Integer getGroupId() {
		return _groupId;
	}
	
	public Integer getId() {
		return _id;
	}
	
	public Integer getPageCount() {
		return _pageCount;
	}
	
	public ArrayList<Message> getMessages() {
		return _messages;
	}
	
	public void load(final Command command) {
		if (_isLoaded) {
			command.callback();
		} else {
			DownloadThemeTask task = new DownloadThemeTask(this, new DownloadThemeTask.Command() {
				public void callback(Theme theme) {
					_pageCount = theme.getPageCount();
					_messages = theme.getMessages();
					_isLoaded = true;
					command.callback();
				}
			});
			task.execute();
		}
	}
}
