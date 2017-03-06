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
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import tools.ParseTools;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/24.
 */
public class NewsAdapter extends BaseAdapter {

    private ArrayList newsList;
    private Context context;


    public NewsAdapter(Context context, ArrayList arrayList){
        newsList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_news,parent,false);
        TextView news_title = (TextView) v.findViewById(R.id.item_news_title);
        TextView news_content = (TextView) v.findViewById(R.id.item_news_content);
        TextView news_time = (TextView) v.findViewById(R.id.item_news_time);
        ImageView news_image = (ImageView) v.findViewById(R.id.item_news_image);


        Map<String,Object> news = (Map<String, Object>) newsList.get(position);
        String title = (String) news.get("title");
        String content = (String) news.get("content");
        String time = (String) news.get("time");
        AVFile image = (AVFile) news.get("image");

        news_title.setText(title);
        news_content.setText(content);
        news_time.setText(time);
        if(image != null) {
            image.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    Drawable drawable = ParseTools.bytes2Drawable(bytes);
                    news_image.setImageDrawable(drawable);
                }
            });
        }
        return v;
    }
}
