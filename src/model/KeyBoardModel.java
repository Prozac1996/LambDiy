package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.avos.avoscloud.*;
import tools.MyTools;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/31.
 * 键盘抽象类，所有键盘都应继承这个类，然后再加入自己的数据
 */
public abstract class KeyBoardModel {

    protected Context context;
    //    protected HashMap<Integer,Integer> loc_map;
    protected HashMap<Integer, Integer> color_map;
    protected Drawable drawable;
    protected String name;

    public KeyBoardModel(Context context, String name) {
        this.context = context;
//        loc_map = setLocMap();
        color_map = setColorMap();
        drawable = setDrawable();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    protected abstract Drawable setDrawable();

//    protected abstract HashMap setLocMap();

    protected abstract HashMap setColorMap();


    //  保存键盘参数并且上传至服务器
    public void saveKeyBoard(String proname, SaveCallback saveCallback) {

        AVObject object = new AVObject("KeyBoard");
        object.put("name", proname);
        object.put("author", AVUser.getCurrentUser());
        object.put("author_name", AVUser.getCurrentUser().getUsername());
        object.put("color_map", color_map.toString());
        object.saveInBackground(saveCallback);
    }

    //  通过destId读取服务器具体键盘参数。
    public void loadKeyBoard(String destId) {
        AVQuery<AVObject> avQuery = new AVQuery<>("KeyBoard");
        avQuery.getInBackground(destId, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    HashMap<String, String> dataMap = MyTools.string2HashMap(avObject.getString("color_map"));
                    for (String key : dataMap.keySet()) {
                        int int_key = Integer.parseInt(key.trim());
                        int int_value = Integer.parseInt(dataMap.get(key).trim());
                        changeColor(int_key, int_value);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    //    改变键盘图层颜色
    public void changeColor(int pos, int color) {
        color_map.put(pos, color);
//        Drawable drawable = findDrawable(loc_map.get(pos),0);
        Drawable drawable = ((LayerDrawable) this.drawable).getDrawable(pos);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    ;

    public void recoverKeyBoard() {
        for (int key : color_map.keySet()) {
//            Drawable drawable = findDrawable(loc_map.get(key),0);
            Drawable drawable = ((LayerDrawable) this.drawable).getDrawable(key);
            drawable.setColorFilter(color_map.get(key), PorterDuff.Mode.SRC_IN);
        }
    }

    //    寻找需要改变颜色的图层
    protected Drawable findDrawable(float x, float y) {
        LayerDrawable ld = (LayerDrawable) drawable;
        final int numberOfLayers = ld.getNumberOfLayers();
        Drawable drawable;
        Bitmap bitmap;
        for (int i = numberOfLayers - 1; i >= 0; i--) {
            drawable = ld.getDrawable(i);
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            try {
                int pixel = bitmap.getPixel((int) x, (int) y);
                if (pixel == Color.TRANSPARENT) {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
            return drawable;
        }
        return null;
    }


}
