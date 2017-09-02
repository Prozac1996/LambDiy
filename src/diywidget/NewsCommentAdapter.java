package diywidget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.*;
import tools.ParseTools;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/24.
 */
public class NewsCommentAdapter extends BaseAdapter {

    private ArrayList commentList;
    private Context context;


    public NewsCommentAdapter(Context context, ArrayList arrayList) {
        commentList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_comment, parent, false);
        TextView tv_author = (TextView) v.findViewById(R.id.item_comment_author);
        TextView tv_content = (TextView) v.findViewById(R.id.item_comment_content);
        ImageView iv_head = (ImageView) v.findViewById(R.id.item_comment_head);


        Map<String, Object> comment = (Map<String, Object>) commentList.get(position);
        AVUser avUser = (AVUser) comment.get("author");
        String content = (String) comment.get("content");

        String keys = "username,userhead";
        avUser.fetchInBackground(keys, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    String username = avObject.getString("username");
                    AVFile head = avObject.getAVFile("userhead");

                    tv_content.setText(content);
                    tv_author.setText(username);
                    if (head != null)
                        head.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, AVException e) {
                                Drawable drawable = ParseTools.bytes2Drawable(bytes);
                                iv_head.setImageDrawable(drawable);
                            }
                        });
                }

            }
        });

//        authorName = (String) object.get("username");
//        head = object.getAVFile("userhead");

//        tv_author.setText(authorName);
//        tv_content.setText(content);
        return v;
    }
}
