package substance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.lcu.lfz.Discovery.R;
import model.KeyBoardModel;
import tools.MyTools;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/31.
 * 小丑鱼键盘实例
 */
public class FishKeyBoard extends KeyBoardModel {


    public FishKeyBoard(Context context) {
        super(context, "小丑鱼");
    }

    @Override
    public Drawable setDrawable() {
        return context.getResources().getDrawable(R.drawable.fish);
    }

//    @Override
//    protected HashMap setLocMap() {
//        HashMap loc_map = new HashMap();
//        loc_map.put(0,0);
//        loc_map.put(1,1);
//        loc_map.put(2,2);
//        loc_map.put(3,4);
//        loc_map.put(4,5);
//        loc_map.put(5,7);
//        loc_map.put(6,8);
//        loc_map.put(7,10);
//        loc_map.put(8,11);
//        loc_map.put(9,13);
//        loc_map.put(10,14);
//        loc_map.put(11,16);
//        loc_map.put(12,17);
//        loc_map.put(13,19);
//        return loc_map;
//    }

    @Override
    protected HashMap setColorMap() {
        HashMap color_map = new HashMap();
        for (int i = 0; i < 14; i++) {
            color_map.put(i, MyTools.randomColor());
        }
        return color_map;
    }


}
