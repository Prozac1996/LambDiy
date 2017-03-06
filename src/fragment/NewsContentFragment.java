package fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.*;
import diywidget.BottomToast;
import diywidget.NewsCommentAdapter;
import tools.ParseTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/10.
 */
public class NewsContentFragment extends Fragment implements View.OnClickListener {

    private TextView tv_title,tv_content;
    private ImageView iv_image;
    private EditText et_comment;
    private Button btn_submit;
    private ListView lv_comment;
    private ScrollView scrollView;

    private AVObject news;
    private NewsCommentAdapter adapter;
    private String title,content;
    private AVFile image;
//    private Map<String,String> commentMap;
    private ArrayList<Map<String,Object>> netCommentList;
//    private String[] strings = {"author","content","head"};
//    private int[] ints = { R.id.item_comment_author,R.id.item_comment_content,R.id.item_comment_head};


    public NewsContentFragment(AVObject news){
        this.news = news;
        title = news.getString("title");
        content = news.getString("content");
        image = news.getAVFile("image");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_content,container,false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        tv_title = (TextView) v.findViewById(R.id.news_title);
        tv_content = (TextView) v.findViewById(R.id.news_content);
        iv_image = (ImageView) v.findViewById(R.id.news_image);
        lv_comment = (ListView) v.findViewById(R.id.news_comment);
        et_comment = (EditText) v.findViewById(R.id.et_comment);
        btn_submit = (Button) v.findViewById(R.id.btn_submit);
        scrollView = (ScrollView) v.findViewById(R.id.news_scrollView);

        btn_submit.setOnClickListener(this);
        tv_title.setText(title);
        tv_content.setText(content);
        if(image != null)
            image.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    Drawable drawable = ParseTools.bytes2Drawable(bytes);
                    iv_image.setImageDrawable(drawable);
                }
            });
        getData();
        scrollView.smoothScrollTo(0,0);
    }

    private void getData() {
        netCommentList = (ArrayList<Map<String,Object>>) news.get("comment");
//        if(netCommentList != null)
//            for(Map<String,Object> map : netCommentList){
//
//                AVUser user;
//                String content;
//                AVFile head;
//                user = (AVUser) map.get("author");
//                content = (String) map.get("content");
//                head = (AVFile) user.get("userhead");
//
//                Map resultMap = new HashMap<>();
//                resultMap.put("author",user);
//                resultMap.put("content",content);
//                resultMap.put("head",head);
//                localCommentList.add(resultMap);
//            }
        if(netCommentList == null)
            netCommentList = new ArrayList<>();
        adapter = new NewsCommentAdapter(getActivity(),netCommentList);
        lv_comment.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        if(netCommentList == null){
            netCommentList = new ArrayList<>();
        }
        String comment_content = et_comment.getText().toString();
        AVUser comment_author = AVUser.getCurrentUser();
        HashMap commentMap = new HashMap();

        commentMap.put("author",comment_author);
        commentMap.put("content",comment_content);
        netCommentList.add(commentMap);
//        localCommentList.add(commentMap);
        news.put("comment",netCommentList);
        et_comment.setText("");
        news.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null) {
                    BottomToast.showToast(getActivity(),"回复成功");
                    adapter.notifyDataSetChanged();
                }else{
                    BottomToast.showToast(getActivity(),"回复成功");
                }
            }
        });
    }
}
