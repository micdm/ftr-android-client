package info.micdm.ftr;

public class Message {

	protected String _author;
	
	protected String _body;
	
	public Message(String author, String body) {
		_author = author;
		_body = body;
	}
	
	public String toString() {
		return _body;
	}
}
