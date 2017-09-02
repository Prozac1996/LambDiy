package fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import cn.lcu.lfz.Discovery.R;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import tools.MyTools;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/2/24.
 */
public class TestFragment extends Fragment {

    private static final int REQUEST_SELECT_IMAGE = 1;

    private Button btn_select, btn_upload;
    private ImageView iv_image;
    private MyTestOnClick myTestOnClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        myTestOnClick = new MyTestOnClick();

        btn_select = (Button) v.findViewById(R.id.test_btn_selectImage);
        btn_upload = (Button) v.findViewById(R.id.test_btn_uploadImage);
        iv_image = (ImageView) v.findViewById(R.id.test_iv_image);

        btn_select.setOnClickListener(myTestOnClick);
        btn_upload.setOnClickListener(myTestOnClick);

        return v;
    }


    class MyTestOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_btn_selectImage:
                    Intent i = new Intent();
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(i, REQUEST_SELECT_IMAGE);
                    break;
                case R.id.test_btn_uploadImage:

                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE) {
                Toast.makeText(getActivity(), "获取到图片", Toast.LENGTH_LONG).show();
                Uri imageUri = data.getData();
                try {
                    AVFile avFile = AVFile.withAbsoluteLocalPath("myHead.png", MyTools.getImageAbsolutePath(getActivity(), imageUri));
                    avFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(getActivity(), "保存完成", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

