package jasic.filip.chatapplication.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import jasic.filip.chatapplication.models.Message;
import jasic.filip.chatapplication.R;
import jasic.filip.chatapplication.providers.MessageProvider;
import jasic.filip.chatapplication.utils.Preferences;


public class MessageAdapter extends BaseAdapter implements View.OnLongClickListener{

    private Context mContext;
    private ArrayList<Message> mMessages;
    private MessageProvider mMessageProvider;

    public MessageAdapter(Context context) {
        mContext = context;
        mMessages = new ArrayList<>();
       // mMessageProvider=new MessageProvider(context);
    }

    public void addMessage(Message message) {
        mMessages.add(message);
    }

    public void update(Message[] messages) {
        mMessages.clear();
        if (messages != null) {
            Collections.addAll(mMessages, messages);
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;
        try {
            rv = mMessages.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            view = inflater.inflate(R.layout.message_row, null);
            ViewHolder holder = new ViewHolder();
            holder.message=view.findViewById(R.id.message1);
            view.setTag(holder);
        }

        Message Message = (Message) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();

        SharedPreferences sharedPref = mContext.getSharedPreferences(Preferences.NAME, Context.MODE_PRIVATE);
       //int loggedInUserId = sharedPref.getInt(Preferences.USER_LOGGED_IN, -1);
        String sender_username = sharedPref.getString("loggedin_username", null);

        if((Message.getSender().compareTo(sender_username))==0) {
            holder.message.setBackgroundColor(view.getResources().getColor(R.color.sendBackgroundColor));
            holder.message.setTextColor(view.getResources().getColor(R.color.sendTextColor));
            holder.message.setGravity(Gravity.END);
        }else{
            holder.message.setBackgroundColor(view.getResources().getColor(R.color.recivedBackground));
            holder.message.setTextColor(view.getResources().getColor(R.color.recivedTextColor));
            holder.message.setGravity(Gravity.START);
        }
        holder.message.setTag(position);
        holder.message.setText(Message.getMessage());

        return view;
    }


    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.message:
               // int position = Integer.parseInt(view.getTag().toString());
                //Message message = mMessages.get(position);
                //mMessageProvider.deleteMessage(message.getId());
                //mMessages.remove(position);
               // notifyDataSetChanged();
                return true;
            default:
                return false;
        }
    }


    private class ViewHolder {
        public TextView message = null;


    }
}