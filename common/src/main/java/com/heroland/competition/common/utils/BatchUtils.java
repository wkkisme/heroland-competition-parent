package com.heroland.competition.common.utils;

import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class BatchUtils {


    public static <T> List<List<T>> split(List<T> list, int num) {
        if (num <= 0) {
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.PARAM_ERROR.getErrorMessage());
        }
        return Stream.iterate(0, k -> k + num).limit(list.size() % num == 0 ? list.size() / num : list.size() / num + 1)
                .parallel()
                .map(k -> list.stream().skip(k).limit(num).parallel().collect(Collectors.toList())
                ).collect(Collectors.toList());
    }
}
