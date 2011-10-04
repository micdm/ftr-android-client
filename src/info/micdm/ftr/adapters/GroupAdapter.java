package info.micdm.ftr.adapters;

import info.micdm.ftr.R;
import info.micdm.ftr.Theme;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Адаптер для списка тем.
 * @author Mic, 2011
 *
 */
public class GroupAdapter extends ItemsAdapter {
	
	/**
	 * Для кэширования.
	 * @author Mic, 2011
	 *
	 */
	class ViewHolder implements AbstractViewHolder {

		/**
		 * Поле с именем автора.
		 */
		TextView author;

		/**
		 * Поле со временем обновления.
		 */
		TextView updated;

		/**
		 * Поле с заголовком.
		 */
		TextView title;
		
		/**
		 * Записывает новые значения в кэш.
		 */
		public void update(Theme theme) {
			author.setText(theme.getAuthor());
			updated.setText(", " + theme.getUpdatedAsString());
			title.setText(theme.getTitle());
		}
	}

	public GroupAdapter(Context context, ArrayList<Theme> themes) {
		super(context, themes);
	}

	@Override
	protected int _getItemLayoutId() {
		return R.layout.theme_list_item;
	}

	@Override
	protected AbstractViewHolder _getNewViewHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.author = (TextView)convertView.findViewById(R.id.themeAuthor);
		holder.updated = (TextView)convertView.findViewById(R.id.themeUpdated);
		holder.title = (TextView)convertView.findViewById(R.id.themeTitle);
		return holder;
	}

	@Override
	protected void _updateViewHolder(AbstractViewHolder holder, Object item) {
		((ViewHolder)holder).update((Theme)item);
	}
}
