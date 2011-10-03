package info.micdm.ftr.utils;

import java.util.Date;

/**
 * Функции для работы с датами.
 * @author Mic, 2011
 *
 */
public class DateUtils {
	
	public static String getRelativeTimeAsString(Date date) {
		Date now = new Date();
		if (now.getTime() - date.getTime() < 5000) {
			return "только что";
		}
		return android.text.format.DateUtils.getRelativeTimeSpanString(date.getTime(), now.getTime(), 0).toString();
	}
}
