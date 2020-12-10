package com.example.parsejsonwithgson.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parsejsonwithgson.R;
import com.example.parsejsonwithgson.model.BoxResult;

import java.util.List;

public class ResultAdapter extends BaseAdapter {
    private Context context;
    private List<BoxResult> lists;

    public ResultAdapter(Context context, List<BoxResult> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            //1.创建布局
            convertView= LayoutInflater.from(context).inflate(R.layout.item_box_whd,null);
            //2.将子view传给viewHolder
            viewHolder.id=convertView.findViewById(R.id.id);
            viewHolder.version=convertView.findViewById(R.id.version);
            viewHolder.name=convertView.findViewById(R.id.name);
            //3.设置TAG 将ViewHolder存储在View中
            convertView.setTag(viewHolder);

        }   else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        BoxResult boxResult=lists.get(position);
        viewHolder.id.setText(boxResult.getId());
        viewHolder.version.setText(boxResult.getVersion());
        viewHolder.name.setText(boxResult.getName());

        return convertView;
    }
    public class ViewHolder{
        public TextView id;
        public TextView version;
        public TextView name;

    }
}
