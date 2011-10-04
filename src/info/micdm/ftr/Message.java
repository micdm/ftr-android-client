package info.micdm.ftr;

import info.micdm.ftr.utils.DateUtils;

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
		return _author + ", " + getCreatedAsString();
	}
	
	/**
	 * Возвращает дату последнего обновления в виде человекопонятной строки.
	 */
	public String getCreatedAsString() {
		return DateUtils.getRelativeTimeAsString(_created).toString();
	}
	
	/**
	 * Возвращает имя автора сообщения.
	 */
	public String getAuthor() {
		return _author;
	}
	
	/**
	 * Возвращает тело сообщения.
	 */
	public String getBody() {
		return _body;
	}
}
