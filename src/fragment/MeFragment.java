package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.lcu.lfz.activity.LoginActivity;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.*;
import tools.MyTools;
import tools.ParseTools;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/1/25.
 * 个人信息界面
 */
public class MeFragment extends Fragment implements View.OnClickListener {

    public static final int SELECT_PHOTO = 1;

    private ImageView iv_head, iv_newHead;
    private TextView tv_name;
    private View v;
    private Button btn_logoff, btn_myPro;
    private Uri uriImage;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Drawable drawable = (Drawable) msg.obj;
            iv_head.setImageDrawable(drawable);
            if (iv_newHead != null)
                iv_newHead.setImageDrawable(drawable);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_me, container, false);
        iv_head = (ImageView) v.findViewById(R.id.fragment_me_head);
        tv_name = (TextView) v.findViewById(R.id.fragment_me_name);
        btn_logoff = (Button) v.findViewById(R.id.fragment_me_logoff);
//        btn_changePwd = (Button) v.findViewById(R.id.btn_change_password);
        btn_myPro = (Button) v.findViewById(R.id.btn_myPro);
        btn_logoff.setOnClickListener(this);
        iv_head.setOnClickListener(this);
//        btn_changePwd.setOnClickListener(this);
        btn_myPro.setOnClickListener(this);
        initData();
        return v;
    }

    private void initData() {
        AVUser user = AVUser.getCurrentUser();
        String name = user.getUsername();
        tv_name.setText(name);
        AVFile headFile = user.getAVFile("userhead");
        if (headFile != null)
            headFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    if (e == null) {
                        Drawable drawable = ParseTools.bytes2Drawable(bytes);
                        Message msg = new Message();
                        msg.obj = drawable;
                        handler.sendMessage(msg);
                    }
                }
            });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_me_head:
                ModifyHead();
                break;
            case R.id.fragment_me_logoff:
                AVUser.logOut();
                Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
                break;
//            case R.id.btn_change_password:
//                LeancloudTools.changePassword();
//                break;
            case R.id.btn_myPro:
                LoadKeyBoardDialogFragment loadKeyBoardDialogFragment = new LoadKeyBoardDialogFragment(null, true);
                loadKeyBoardDialogFragment.show(getFragmentManager(), "load_me_keyboard");
                break;
        }
    }


    private void ModifyHead() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        v = inflater.inflate(R.layout.dialog_modify_head, null);
        iv_newHead = (ImageView) v.findViewById(R.id.img_newHead);
        iv_newHead.setImageDrawable(iv_head.getDrawable());
        iv_newHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, SELECT_PHOTO);
            }
        });
        builder.setView(v);
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            uriImage = data.getData();
            ContentResolver cr = getActivity().getContentResolver();
            try {

                AVFile avFile = AVFile.withAbsoluteLocalPath(AVUser.getCurrentUser().getUsername() + "_head.png", MyTools.getImageAbsolutePath(getActivity(), uriImage));
                AVUser.getCurrentUser().put("userhead", avFile);
                AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Toast.makeText(getActivity(), "头像已修改好！", Toast.LENGTH_SHORT).show();
                            AVFile newHead = AVUser.getCurrentUser().getAVFile("userhead");
                            newHead.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, AVException e) {
                                    Drawable drawable = ParseTools.bytes2Drawable(bytes);
                                    Message msg = new Message();
                                    msg.obj = drawable;
                                    handler.sendMessage(msg);
                                }
                            });

                        }
                    }
                });

//                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uriImage));
//                bitmap = MyTools.comp(bitmap);
//                ImageView imageView = (ImageView) v.findViewById(R.id.img_newHead);
//                imageView.setImageDrawable(ParseTools.bitmap2Drawable(bitmap));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
