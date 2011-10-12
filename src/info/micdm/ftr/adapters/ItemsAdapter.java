package info.micdm.ftr.adapters;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Абстрактный адаптер для списка.
 * @author Mic, 2011
 *
 */
public abstract class ItemsAdapter extends BaseAdapter {
	
	/**
	 * Для кэширования.
	 * @author Mic, 2011
	 *
	 */
	interface AbstractViewHolder {
		
	}
	
	/**
	 * Этот товарищ будет создавать новый элемент из XML.
	 */
	protected LayoutInflater _inflater;
	
	/**
	 * Список сообщений.
	 */
	protected ArrayList<?> _items;
	
	public ItemsAdapter(Context context, ArrayList<?> items) {
		_inflater = LayoutInflater.from(context);
		_items = items;
	}
	
	/**
	 * Возвращает количество элементов.
	 */
	@Override
	public int getCount() {
		return (_items == null) ? 0 : _items.size();
	}
	
	/**
	 * Возвращает элемент в указанной позиции.
	 */
	@Override
	public Object getItem(int position) {
		return _items.get(position);
	}

	/**
	 * Возвращает идентификатор элемента в указанной позиции.
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * Возвращает идентификатор разметки, которая будет использоваться для элементов списка.
	 */
	protected abstract int _getItemLayoutId();
	
	/**
	 * Создает и возвращает новый кэш.
	 */
	protected abstract AbstractViewHolder _getNewViewHolder(View convertView);
	
	/**
	 * Обновляет кэш.
	 */
	protected abstract void _updateViewHolder(AbstractViewHolder holder, Object item);

	/**
	 * Возвращает заполненный элемент списка.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AbstractViewHolder holder;
		if (convertView == null) {
			convertView = _inflater.inflate(_getItemLayoutId(), null);
			holder = _getNewViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (AbstractViewHolder)convertView.getTag();
		}
		_updateViewHolder(holder, getItem(position));
		return convertView;
	}
	
	/**
	 * Добавляет элементы в список.
	 */
	public void addItems(ArrayList<?> items) {
		if (_items == null) {
			_items = items;
		} else {
			_items.addAll((Collection)items);
		}
		notifyDataSetChanged();
	}
}
