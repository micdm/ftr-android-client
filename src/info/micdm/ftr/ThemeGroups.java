package info.micdm.ftr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Список рубрик.
 * @author Mic, 2011
 *
 */
public class ThemeGroups {

	/**
	 * Синглтон.
	 */
	protected static ThemeGroups _instance;
	
	/**
	 * Список рубрик.
	 */
	protected HashMap<Integer, ThemeGroup> _groups = new HashMap<Integer, ThemeGroup>();
	
	/**
	 * Синглтон.
	 */
	public static ThemeGroups getInstance() {
		if (_instance == null) {
			_instance = new ThemeGroups();
			_instance._createGroups();
		}
		return _instance;
	}
	
	/**
	 * Создает список групп.
	 */
	protected void _createGroups() {
		HashMap<Integer, String> groups = new HashMap<Integer, String>();
		groups.put(0, "Горячее");
		groups.put(4, "Компьютеры");
		groups.put(5, "Матоязычные");
		groups.put(8, "Культура");
		groups.put(10, "Афиша");
		groups.put(12, "Общение");
		groups.put(15, "Развлечения");
		groups.put(21, "Наука и техника");
		groups.put(23, "Разное");
		groups.put(24, "Политика");
		groups.put(27, "Барахолка");
		groups.put(28, "Вопрос -> ответ");
		groups.put(29, "Отдых и спорт");
		groups.put(30, "Кино и музыка");
		groups.put(32, "Работа");
		groups.put(33, "Бизнес");
		groups.put(34, "Знакомства");
		groups.put(35, "Детский мир");
		groups.put(36, "Образование");
		groups.put(38, "Нетрадиционные");
		groups.put(39, "Мобилизация");
		groups.put(43, "Недвижимость");
		groups.put(44, "Секс");
		groups.put(47, "Автомобильная");
		groups.put(49, "Мусорное ведро");
		groups.put(50, "Барахолка мобильников");
		groups.put(51, "Женская");
		groups.put(52, "Авторынок");
		groups.put(53, "Здоровье");
		groups.put(55, "Барахолка компьютеров");
		groups.put(56, "Услуги компьютерные");
		groups.put(57, "Услуги связи");
		groups.put(58, "Автоуслуги");
		groups.put(59, "Спорттовары");
		groups.put(60, "Мужской клуб");
		groups.put(61, "Услуги");
		groups.put(62, "Зоомир");
		groups.put(63, "Новости");
		groups.put(64, "Барахолка детская");
		groups.put(65, "Барахолка женская");
		groups.put(66, "Зоорынок");
		groups.put(68, "Аптека");
		for (Map.Entry<Integer, String> item : groups.entrySet()) {
			_groups.put(item.getKey(), new ThemeGroup(item.getKey(), item.getValue()));
		}
	}
	
	/**
	 * Возвращает все группы, отсортированные по алфавиту.
	 */
	public ArrayList<ThemeGroup> getAllGroups() {
		ArrayList<ThemeGroup> groups = new ArrayList<ThemeGroup>(_groups.values());
		Collections.sort(groups);
		return groups;
	}
	
	/**
	 * Возвращает группу по идентификатору.
	 */
	public ThemeGroup getGroup(Integer id) {
		if (_groups.containsKey(id)) {
			return _groups.get(id);
		}
		return null;
	}
}
