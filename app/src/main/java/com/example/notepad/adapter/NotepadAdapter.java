package com.example.notepad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.notepad.R;
import com.example.notepad.bean.NotepadBean;

import java.util.List;

public class NotepadAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<NotepadBean> list;

    public NotepadAdapter(Context context, List<NotepadBean> list){
        this.layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount(){
        return list==null ? 0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    class ViewHolder{
        TextView tvNotepadContent;
        TextView tvNotepadTime;
        public ViewHolder(View view){
            tvNotepadContent = (TextView) view.findViewById(R.id.tv_item_brief);
            tvNotepadTime = (TextView) view.findViewById(R.id.tv_item_time);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  viewHolder;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.notepad_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NotepadBean noteInfo = (NotepadBean) getItem(position);
        viewHolder.tvNotepadContent.setText(noteInfo.getNotepadContent());
        viewHolder.tvNotepadTime.setText(noteInfo.getNotepadTime());
        return convertView;
    }
}
