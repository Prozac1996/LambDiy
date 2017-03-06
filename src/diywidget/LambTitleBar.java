package diywidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.lcu.lfz.Discovery.R;

/**
 * Created by Administrator on 2017/2/26.
 */
public class LambTitleBar extends RelativeLayout {

    private Context context;

    private String mLeftButtonText;
    private int mLeftButtonTextColor;
    private int mLeftButtonTextSize;
    private Drawable mLeftButtonImage;
    private String mRightButtonText;
    private int mRightButtonTextColor;
    private int mRightButtonTextSize;
    private Drawable mRightButtonImage;
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private TextView mLeftTextView,mTitleTextView,mRightTextView;
    private ImageView mLeftImageView,mRightImageView;



    public LambTitleBar(Context context) {
        super(context);
        this.context = context;

    }

    public LambTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context,attrs);
        initView();
    }


    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LambTitleBar);
        mLeftButtonText = typedArray.getString(R.styleable.LambTitleBar_leftButtonText);
        mLeftButtonTextColor = typedArray.getColor(R.styleable.LambTitleBar_leftButtonTextColor, Color.GRAY);
        mLeftButtonTextSize = typedArray.getDimensionPixelSize(R.styleable.LambTitleBar_leftButtonTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mLeftButtonImage = typedArray.getDrawable(R.styleable.LambTitleBar_leftButtonImage);

        mRightButtonText = typedArray.getString(R.styleable.LambTitleBar_rightButtonText);
        mRightButtonTextColor = typedArray.getColor(R.styleable.LambTitleBar_rightButtonTextColor, Color.GRAY);
        mRightButtonTextSize = typedArray.getDimensionPixelSize(R.styleable.LambTitleBar_rightButtonTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mRightButtonImage = typedArray.getDrawable(R.styleable.LambTitleBar_rightButtonImage);

        mTitleText = typedArray.getString(R.styleable.LambTitleBar_centerButtonText);
        mTitleTextColor = typedArray.getColor(R.styleable.LambTitleBar_centerButtonTextColor, Color.GRAY);
        mTitleTextSize = typedArray.getDimensionPixelSize(R.styleable.LambTitleBar_centerButtonTextSize, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
    }

    private void initView(){
        if(mLeftButtonImage == null & mLeftButtonText != null){
            mLeftTextView = new TextView(context);
            mLeftTextView.setText(mLeftButtonText);
            mLeftTextView.setTextColor(mLeftButtonTextColor);
            mLeftTextView.setTextSize(mLeftButtonTextSize);
            LayoutParams leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            leftParams.addRule(ALIGN_PARENT_LEFT);
            leftParams.addRule(CENTER_VERTICAL);
            addView(mLeftTextView,leftParams);
        }else if(mLeftButtonImage != null){
            mLeftImageView = new ImageView(context);
            LayoutParams leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            leftParams.addRule(ALIGN_PARENT_LEFT);
            leftParams.addRule(CENTER_VERTICAL);
            mLeftImageView.setImageDrawable(mLeftButtonImage);
            addView(mLeftImageView,leftParams);
        }

        if(mTitleText != null){
            mTitleTextView = new TextView(context);
            mTitleTextView.setText(mTitleText);
            mTitleTextView.setTextSize(mTitleTextSize);
            mTitleTextView.setTextColor(mTitleTextColor);
            LayoutParams centerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            centerParams.addRule(CENTER_IN_PARENT);
            addView(mTitleTextView,centerParams);
        }

        if(mRightButtonImage == null & mRightButtonText != null){
            mRightTextView = new TextView(context);
            mRightTextView.setText(mRightButtonText);
            mRightTextView.setTextColor(mRightButtonTextColor);
            mRightTextView.setTextSize(mRightButtonTextSize);
            LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightParams.addRule(ALIGN_PARENT_RIGHT);
            rightParams.addRule(CENTER_VERTICAL);
            addView(mRightTextView,rightParams);
        }else if(mRightButtonImage != null){
            mRightImageView = new ImageView(context);
            LayoutParams rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rightParams.addRule(ALIGN_PARENT_RIGHT);
            rightParams.addRule(CENTER_VERTICAL);
            mRightImageView.setImageDrawable(mRightButtonImage);
            addView(mRightImageView,rightParams);
        }
    }

    public interface OnButtonClickListener{
        void onLeftClick();
        void onRightClick();
    }

    public void setButtonClickListener(OnButtonClickListener listener){
        if(listener != null){
            if(mLeftTextView != null){
                mLeftTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onLeftClick();
                    }
                });
            }
            if(mLeftImageView != null){
                mLeftImageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onLeftClick();
                    }
                });
            }
            if(mRightTextView != null){
                mRightTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onRightClick();
                    }
                });
            }
            if(mRightImageView != null){
                mRightImageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onRightClick();
                    }
                });
            }
        }
    }
}
