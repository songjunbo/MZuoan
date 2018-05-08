package zuoan.com.mzuoan.base;

import java.lang.reflect.ParameterizedType;

/**
 * Created by 15225 on 2018/2/7.
 */

public class CreateUtil {

    static <T> T getT(Object o, int i) {
        try {

            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
