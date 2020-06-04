package com.heroland.competition.util;


import com.alijk.bqhealth.cloud.vo.BaseVO;

import java.util.List;

/**
 * bqhealth-cloud-parent
 *
 * @author wangkai
 * @date 2019/4/25
 */

public class MppingUtil{

    public static <T extends BaseVO> void setModifierMpping(List<T> data,String modifier){

        for (T datum : data) {
            datum.setModifier(modifier);
        }

    }
}
