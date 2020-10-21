package com.heroland.competition.service.admin;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.domain.dp.HerolandQuestionBankDP;
import com.heroland.competition.domain.dp.HerolandQuestionBankImportDP;
import com.heroland.competition.domain.dto.HeroLandQuestionBankDto;
import com.heroland.competition.domain.dto.HeroLandQuestionBankListForTopicDto;
import com.heroland.competition.domain.dto.HeroLandQuestionBankSimpleDto;
import com.heroland.competition.domain.request.HerolandQuestionBankListForChapterRequest;
import com.heroland.competition.domain.request.HerolandQuestionBankPageRequest;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/12
 */

public interface HeroLandQuestionBankService {

    Boolean createQuestion(HerolandQuestionBankDP dp);


    Boolean importQuestion(List<HerolandQuestionBankImportDP> importDPS);

    Boolean editQuestion(HerolandQuestionBankDP dp);

    /**
     * 根据全局id进行删除
     * 所有快照号的都删除
     * @param ids
     * @return
     */
    Boolean deleteByqtId(String ids);

    /**
     * 根据id进行删除
     * 只删除当前快照号的
     * @param id
     * @return
     */
    Boolean deleteById(Long id);

    HeroLandQuestionBankDto getById(Long id);

    HeroLandQuestionBankDto getByQtId(String qtId);

    PageResponse<HeroLandQuestionBankSimpleDto> pageQuery(HerolandQuestionBankPageRequest request);

    PageResponse<HeroLandQuestionBankListForTopicDto> getQuestionList(HerolandQuestionBankListForChapterRequest request);


    ResponseBody<Boolean> importQuestions(MultipartHttpServletRequest request,Integer bankType) throws Exception;

    ResponseBody<Boolean> importQuestionBankExcel(MultipartHttpServletRequest request,Integer bankType) throws Exception;
}
