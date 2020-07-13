package com.heroland.competition.service.questionBank;

import com.heroland.competition.domain.dto.HeroLandQuestionBankDto;
import com.heroland.competition.domain.request.HerolandQuestionBankRequest;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/12
 */

public interface HeroLandQuestionBankService {

    int createQuestion(HerolandQuestionBankRequest request);

    int editQuestion(HerolandQuestionBankRequest request);

    int delete(HerolandQuestionBankRequest request);

    HeroLandQuestionBankDto getById(HerolandQuestionBankRequest request);

    List<HeroLandQuestionBankDto> pageQuery(HerolandQuestionBankRequest request);
}
