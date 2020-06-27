package com.heroland.competition.controller;


import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HeroLandTopicGroupDP;
import com.heroland.competition.domain.qo.HeroLandQuestionQO;
import com.heroland.competition.service.HeroLandQuestionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * heroland-competition-parent
 *
 * @author wangkai
 * @date 2020/6/19
 */

@RestController
@RequestMapping("/heroland/topic")
public class HeroLandTopicQuestionController {

    @Resource
    private HeroLandQuestionService heroLandQuestionService;

    /**
     * 查询某个题组下的所有题目
     * @param heroLandQuestionQO h
     * @return e
     */
    @RequestMapping("/queryQuestions")
    public ResponseBody<List<HeroLandTopicGroupDP>> getTopicQuestion(@RequestBody HeroLandQuestionQO heroLandQuestionQO){

        return heroLandQuestionService.getTopicQuestions(heroLandQuestionQO);
    }

}
