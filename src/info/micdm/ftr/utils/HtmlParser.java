package info.micdm.ftr.utils;

import java.util.Date;

/**
 * Как бы парсер HTML-страниц.
 * @author Mic, 2011
 *
 */
public abstract class HtmlParser {

	/**
	 * Если указанная дата находится в будущем, возвращает текущую дату.
	 */
	protected Date _getCorrectDate(Date probablyCorrect) {
		Date now = new Date();
		return probablyCorrect.getTime() > now.getTime() ? now : probablyCorrect;
	}
}
