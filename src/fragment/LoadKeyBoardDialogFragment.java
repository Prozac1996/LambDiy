package fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/6.
 */
public class LoadKeyBoardDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    private ListView lv_keyboard;
    private ProgressBar progressBar;
    private Adapter adapter;
    private List<AVObject> list_keyboard;
    private String[] array_from= new String[] {"logo","pro","user"};
    private int[] array_to = new int[] {R.id.item_icon,R.id.item_pro,R.id.item_user};
    private LoadKeyBoardEventListener listener;
    private boolean flag;

    public LoadKeyBoardDialogFragment(LoadKeyBoardEventListener listener,boolean flag){
        this.listener = listener;
        this.flag = flag;
    }

    public interface LoadKeyBoardEventListener{
        void onLoad(String destId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_list,container,false);
        lv_keyboard = (ListView) v.findViewById(R.id.lv_keyboard);
        lv_keyboard.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) v.findViewById(R.id.dialog_list_progressBar);
        initData();

        return v;
    }

    private List<Map<String,Object>> getData() {
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for(AVObject obj : list_keyboard){
            Map<String,Object> map = new HashMap<>();
            map.put("logo",R.drawable.keyboard);
            map.put("pro",obj.getString("name"));
            map.put("user",obj.getString("author_name"));
            list.add(map);
        }
        return list;
    }

    private void initData() {
        AVQuery avQuery = new AVQuery("KeyBoard");
        if(flag)
            avQuery.whereEqualTo("author_name",AVUser.getCurrentUser().getUsername());
        avQuery.findInBackground(new FindCallback() {
            @Override
            public void done(List list, AVException e) {
                progressBar.setVisibility(View.GONE);
                lv_keyboard.setVisibility(View.VISIBLE);
                list_keyboard = list;
                adapter = new SimpleAdapter(getActivity(),getData(),R.layout.item_list_load,array_from,array_to);
                lv_keyboard.setAdapter((ListAdapter) adapter);
                lv_keyboard.setOnItemClickListener(LoadKeyBoardDialogFragment.this);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(listener != null)
            listener.onLoad(list_keyboard.get(position).getObjectId());
        dismiss();
    }
}
