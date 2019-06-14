package cn.eblcu.questionbank.infrastructure.util;

import java.util.List;

public class CommonUtils {

    boolean listIsEmptyOrNull(List list) {

        if (null == list)
            return true;

        return list.size() <= 0;
    }
}
