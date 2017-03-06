package tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2017/2/24.
 */
public class ParseTools {

    public static Drawable bytes2Drawable(byte[] b){
        Bitmap bitmap = bytes2Bitmap(b);
        return bitmap2Drawable(bitmap);
    }

    public static Bitmap bytes2Bitmap(byte[] b){
        if(b.length !=0){
            return BitmapFactory.decodeByteArray(b,0,b.length);
        }
        return null;
    }

    public static Bitmap Drawable2Bitmap(Drawable drawable){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),
                drawable.getOpacity()!= PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] bitmap2ByteArray(Bitmap bmp){
        int bytes = bmp.getByteCount();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();
        return byteArray;
    }

    public static Drawable bitmap2Drawable(Bitmap bmp){
        BitmapDrawable bd = new BitmapDrawable(bmp);
        Drawable d = bd;
        return d;
    }

    public static byte[] Image2ByteArray(Uri uri){
        File file = new File(URI.create(uri.toString()));
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum;(readNum = fis.read(buf)) != -1;){
                baos.write(buf,0,readNum);
            }
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable uri2Drawable(Uri uri){
        return null;
    }
}
