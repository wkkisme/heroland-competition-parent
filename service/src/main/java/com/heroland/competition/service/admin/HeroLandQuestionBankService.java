package com.heroland.competition.service.admin;

import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandQuestionBankDP;
import com.heroland.competition.domain.dto.HeroLandQuestionBankDto;
import com.heroland.competition.domain.dto.HeroLandQuestionBankSimpleDto;
import com.heroland.competition.domain.request.HerolandQuestionBankPageRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankRequest;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/12
 */

public interface HeroLandQuestionBankService {

    Boolean createQuestion(HerolandQuestionBankDP dp);

    Boolean editQuestion(HerolandQuestionBankDP dp);

    Boolean delete(Long id);

    HeroLandQuestionBankDto getById(Long id);

    PageResponse<HeroLandQuestionBankSimpleDto> pageQuery(HerolandQuestionBankPageRequest request);
}
