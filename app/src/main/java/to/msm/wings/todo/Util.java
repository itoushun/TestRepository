package to.msm.wings.todo;

import android.app.Activity;
import android.util.TypedValue;

/**
 * Created by 4163203 on 2017/09/14.
 */

public class Util {

    public static int dp2px(Activity activity,float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,activity.getResources().getDisplayMetrics());
    }
}
