package info.micdm.ftr.adapters;

import info.micdm.ftr.Message;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Адаптер для списка сообщений.
 * @author Mic, 2011
 *
 */
public class ThemeAdapter extends ItemsAdapter {
	
	/**
	 * Для кэширования.
	 * @author Mic, 2011
	 *
	 */
	class ViewHolder implements AbstractViewHolder {
		
		TextView author;
		
		TextView created;
		
		TextView body;
	}

	public ThemeAdapter(Context context, ArrayList<Message> messages) {
		super(context, messages);
	}

	@Override
	protected AbstractViewHolder _getNewViewHolder(View convertView) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void _updateViewHolder(AbstractViewHolder holder, Object item) {
		// TODO Auto-generated method stub
		
	}
}
