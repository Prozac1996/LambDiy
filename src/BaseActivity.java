import android.app.Activity;
import android.os.Bundle;
import tools.ACache;

/**
 * Created by Administrator on 2017/9/2.
 */
public class BaseActivity extends Activity {
    public ACache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCache = ACache.get(this);
    }

}
