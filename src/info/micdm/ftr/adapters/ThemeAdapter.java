package info.micdm.ftr.adapters;

import info.micdm.ftr.Message;
import info.micdm.ftr.R;

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
		
		/**
		 * Поле с именем автора.
		 */
		TextView author;
		
		/**
		 * Поле с датой добавления сообщения.
		 */
		TextView created;
		
		/**
		 * Поле с телом сообщения.
		 */
		TextView body;
		
		/**
		 * Записывает новые значения в кэш.
		 */
		public void update(Message message) {
			author.setText(message.getAuthor());
			created.setText(", " + message.getCreatedAsString());
			body.setText(message.getBody());
		}
	}

	public ThemeAdapter(Context context, ArrayList<Message> messages) {
		super(context, messages);
	}
	
	public ThemeAdapter(Context context) {
		super(context, null);
	}
	
	@Override
	protected int _getItemLayoutId() {
		return R.layout.message_list_item;
	}

	@Override
	protected AbstractViewHolder _getNewViewHolder(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.author = (TextView)convertView.findViewById(R.id.messageAuthor);
		holder.created = (TextView)convertView.findViewById(R.id.messageCreated);
		holder.body = (TextView)convertView.findViewById(R.id.messageBody);
		return holder;
	}

	@Override
	protected void _updateViewHolder(AbstractViewHolder holder, Object item) {
		((ViewHolder)holder).update((Message)item);		
	}
}
