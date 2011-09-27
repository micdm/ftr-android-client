package info.micdm.ftr;

import java.util.Date;

/**
 * Отдельное сообщение.
 * @author Mic, 2011
 *
 */
public class Message {

	/**
	 * Идентификатор сообщения.
	 */
	protected Integer _id;
	
	/**
	 * Дата добавления.
	 */
	protected Date _created;
	
	/**
	 * Автор сообщения.
	 */
	protected String _author;
	
	/**
	 * Тело сообщения.
	 */
	protected String _body;
	
	public Message(Integer id, Date created, String author, String body) {
		_id = id;
		_created = created;
		_author = author;
		_body = body;
	}
	
	public String toString() {
		return "[" + _id + "] " + _author + ", " + _created.toString() + "\n" + _body;
	}
}
