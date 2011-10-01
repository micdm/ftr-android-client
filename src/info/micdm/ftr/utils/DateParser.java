package info.micdm.ftr.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import info.micdm.ftr.utils.Log;

/**
 * Парсер дат.
 * @author Mic, 2011
 *
 */
public class DateParser {
	
	/**
	 * Типы форматов.
	 */
	public enum Type {
		THEME,
		MESSAGE
	};
	
	/**
	 * Разные форматы.
	 */
	protected static HashMap<Type, SimpleDateFormat> _formats = new HashMap<DateParser.Type, SimpleDateFormat>();
	
	/**
	 * Возвращает формат для указанного типа.
	 */
	protected static SimpleDateFormat _getFormat(Type type) {
		if (!_formats.containsKey(type)) {
			SimpleDateFormat format = null;
			if (type == Type.THEME) {
				format = new SimpleDateFormat("kk:mm dd/MM");
			}
			if (type == Type.MESSAGE) {
				format = new SimpleDateFormat("dd.MM.yyyy (kk:mm)");
			}
			format.setTimeZone(TimeZone.getTimeZone("Asia/Novosibirsk"));
			_formats.put(type, format);
		}
		return _formats.get(type);
	}
	
	/**
	 * Преобразовывает текст в дату для указанного типа даты.
	 */
	public static Date parse(String text, Type type) {
		SimpleDateFormat format = _getFormat(type);
		try {
			Date result = format.parse(text);
			if (type == Type.THEME) {
				result.setYear(new Date().getYear());
			}
			return result;
		} catch (ParseException e) {
			Log.debug("can't parse date " + text);
			return null;
		}
	}
}
