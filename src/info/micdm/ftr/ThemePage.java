package info.micdm.ftr;

import java.util.ArrayList;

/**
 * Одна страница темы.
 * @author Mic, 2011
 *
 */
public class ThemePage {
	
	/**
	 * Список сообщений на странице.
	 */
	protected ArrayList<Message> _messages;
	
	public ThemePage(ArrayList<Message> messages) {
		_messages = messages;
	}
	
	/**
	 * Возвращает список сообщений.
	 */
	public ArrayList<Message> getMessages() {
		return _messages;
	}
}
