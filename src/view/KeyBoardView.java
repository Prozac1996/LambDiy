package view;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import model.KeyBoardModel;
import substance.FishKeyBoard;

/**
 * Created by Administrator on 2017/1/31.
 * 此自定义View作用是生成键盘的画布，作为载体。
 */
public class KeyBoardView extends View {

    //    private Paint mPaint;
    private LayerDrawable mDrawables;           //获取分层合成的总Drawable
    public KeyBoardModel keyboardModel = null;  //具体的设计对象
    public OnKeyBoardSelectedListener listener;
    private boolean load;

    public KeyBoardView(Context context) {
        super(context);
//               废弃代码：将具体实现包含到KeyBoardModel类之中，便于实现重用。
//        mPaint = new Paint();
//        Drawable drawable = getResources().getDrawable(R.drawable.fish);
//
//        setBackground(drawable);
//        mDrawables = (LayerDrawable) getBackground();
        load = false;
    }

    public boolean isLoad() {
        return load;
    }

    //更换键盘Model时调用
    public void changeKeyboard(int kb_id, OnKeyBoardSelectedListener listener) {
        load = true;
        this.listener = listener;
        switch (kb_id) {
            case 1:
                keyboardModel = new FishKeyBoard(getContext());
                break;
            default:
                break;
        }
        if (keyboardModel != null) {
            mDrawables = (LayerDrawable) keyboardModel.getDrawable();
            setBackground(mDrawables);
            keyboardModel.recoverKeyBoard();
            listener.onSelectedKeyBoard(keyboardModel.getName());
        }
    }

    //更换层次颜色时调用
    public void changeColor(int pos, int color) {
        keyboardModel.changeColor(pos, color);
        //        Drawable drawable;
//        if(drawable != null){
//            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
//        }
    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
////        canvas.drawColor(Color.WHITE);
//        mPaint.setColor(Color.BLACK);
//        mPaint.setStrokeWidth(3);
//        mPaint.setTextSize(40);
//        canvas.drawText("选择您想设计的键盘",50,50,mPaint);
//    }


    //    废弃代码：寻找需要涂色的图层，已集成到KeyBoardModel类中
//    private Drawable findDrawable(float x,float y){
//        final int numberOfLayers = mDrawables.getNumberOfLayers();
//        Drawable drawable = null;
//        Bitmap bitmap;
//        for(int i = numberOfLayers - 1;i >= 0; i--){
//            drawable = mDrawables.getDrawable(i);
//            bitmap = ((BitmapDrawable)drawable).getBitmap();
//            try {
//                int pixel = bitmap.getPixel((int) x, (int) y);
//                if (pixel == Color.TRANSPARENT) {
//                    continue;
//                }
//            }catch (Exception e){
//                continue;
//            }
//            return drawable;
//        }
//        return null;
//    }
//
    public interface OnKeyBoardSelectedListener {
        public void onSelectedKeyBoard(String name);

        public void onSelectedArea(String name);
    }
}