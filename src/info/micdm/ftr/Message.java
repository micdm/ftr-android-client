package info.micdm.ftr;

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
	 * Автор сообщения.
	 */
	protected String _author;
	
	/**
	 * Тело сообщения.
	 */
	protected String _body;
	
	public Message(Integer id, String author, String body) {
		_id = id;
		_author = author;
		_body = body;
	}
	
	public String toString() {
		return _id + "\n" + _body;
	}
}
