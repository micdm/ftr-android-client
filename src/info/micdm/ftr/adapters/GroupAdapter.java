package info.micdm.ftr.adapters;

import info.micdm.ftr.R;
import info.micdm.ftr.Theme;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Адаптер для списка тем.
 * @author Mic, 2011
 *
 */
public class GroupAdapter extends BaseAdapter {
	
	/**
	 * Класс для кэширования, как предлагают в официальных примерах.
	 * @author Mic, 2011
	 *
	 */
	static class ViewHolder {
		TextView author;
		TextView updated;
		TextView title;
	}
	
	/**
	 * Этот товарищ будет создавать новый элемент из XML.
	 */
	protected LayoutInflater _inflater;
	
	/**
	 * Список тем.
	 */
	protected ArrayList<Theme> _themes;
	
	public GroupAdapter(Context context, ArrayList<Theme> themes) {
		_inflater = LayoutInflater.from(context);
		_themes = themes;
	}
	
	/**
	 * Возвращает количество элементов.
	 */
	public int getCount() {
		return _themes.size();
	}
	
	/**
	 * Возвращает элемент в указанной позиции.
	 */
	public Object getItem(int position) {
		return _themes.get(position);
	}

	/**
	 * Возвращает идентификатор элемента в указанной позиции.
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * Возвращает заполненный элемент списка.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = _inflater.inflate(R.layout.theme_list_item, null);
			holder = new ViewHolder();
			holder.author = (TextView)convertView.findViewById(R.id.themeAuthor);
			holder.updated = (TextView)convertView.findViewById(R.id.themeUpdated);
			holder.title = (TextView)convertView.findViewById(R.id.themeTitle);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		Theme theme = (Theme)getItem(position);
		holder.author.setText(theme.getAuthor());
		holder.updated.setText(", " + theme.getUpdatedAsString());
		holder.title.setText(theme.getTitle());
		return convertView;
	}
}