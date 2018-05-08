package zuoan.com.mzuoan.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 15225 on 2018/2/3.
 */

public class ActivityManager {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
