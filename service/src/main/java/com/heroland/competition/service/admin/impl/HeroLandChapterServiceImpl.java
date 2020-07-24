package com.heroland.competition.service.admin.impl;

import com.anycommon.response.utils.ResponseBodyWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.heroland.competition.common.contants.ChapterEnum;
import com.heroland.competition.common.contants.KnowledgeReferEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandChapterMapper;
import com.heroland.competition.dal.mapper.HerolandKnowledgeMapper;
import com.heroland.competition.dal.mapper.HerolandKnowledgeReferMapper;
import com.heroland.competition.dal.pojo.*;
import com.heroland.competition.dal.pojo.basic.HerolandKnowledge;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandChapterDP;
import com.heroland.competition.domain.dto.HerolandChapterDto;
import com.heroland.competition.domain.dto.HerolandChapterSimpleDto;
import com.heroland.competition.domain.dto.HerolandKnowledgeSimpleDto;
import com.heroland.competition.domain.qo.HerolandKnowledgeQO;
import com.heroland.competition.domain.request.HerolandChapterKnowledgeRequest;
import com.heroland.competition.domain.request.HerolandChapterPageRequest;
import com.heroland.competition.domain.request.HerolandPreChapterRequest;
import com.heroland.competition.service.admin.HeroLandAdminService;
import com.heroland.competition.service.admin.HeroLandChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 */
@Component
@Slf4j
public class HeroLandChapterServiceImpl implements HeroLandChapterService {


    @Resource
    private HerolandChapterMapper heroLandChapterMapper;

    @Resource
    private HerolandKnowledgeReferMapper herolandKnowledgeReferMapper;

    @Resource
    private HerolandKnowledgeMapper herolandKnowledgeMapper;

    @Resource
    private HeroLandAdminService heroLandAdminService;

    @Override
    @Transactional
    public Boolean add(HerolandChapterDP dp) {
        if (!NumberUtils.nullOrZeroLong(dp.getId())){
           return edit(dp);
        }
        //todo 纠正一下顺序，如果顺序已经有了，则抛出异常，提醒
        //目前的章节细粒度太小，所以这个order有可能重复，暂且放着
        dp = dp.checkAndBuildBeforeCreate();
        HerolandChapter herolandChapter = new HerolandChapter();
        herolandChapter.setContent(dp.getContent());
        herolandChapter.setContentType(dp.getContentType());
        herolandChapter.setCourse(dp.getCourse());
        herolandChapter.setEdition(dp.getEdition());
        herolandChapter.setGrade(dp.getGrade());
        herolandChapter.setOrder(dp.getOrder());
        herolandChapter.setParentId(dp.getParentId());
        heroLandChapterMapper.insertSelective(herolandChapter);
        if (!CollectionUtils.isEmpty(dp.getKnowledges())){
            List<HerolandKnowledgeRefer> list = Lists.newArrayList();
            dp.getKnowledges().stream().forEach(e -> {
                HerolandKnowledgeRefer refer = new HerolandKnowledgeRefer();
                refer.setKnowledgeId(e);
                refer.setReferId(herolandChapter.getId());
                refer.setType(KnowledgeReferEnum.CHAPTER.getType());
                list.add(refer);
            });
            herolandKnowledgeReferMapper.batchSave(list);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean edit(HerolandChapterDP dp) {
        AssertUtils.notNull(dp.getId());
        HerolandChapter herolandChapter = new HerolandChapter();
        herolandChapter.setContent(dp.getContent());
        herolandChapter.setContentType(dp.getContentType());
        herolandChapter.setCourse(dp.getCourse());
        herolandChapter.setEdition(dp.getEdition());
        herolandChapter.setGrade(dp.getGrade());
        herolandChapter.setOrder(dp.getOrder());
        herolandChapter.setParentId(dp.getParentId());
        herolandChapter.setId(dp.getId());
       int update = heroLandChapterMapper.updateByPrimaryKeySelective(herolandChapter);
       if (update == 0){
           return false;
       }
        List<HerolandKnowledgeRefer> refers = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(dp.getKnowledges())){
            dp.getKnowledges().stream().forEach(e -> {
                HerolandKnowledgeRefer refer = new HerolandKnowledgeRefer();
                refer.setKnowledgeId(e);
                refer.setReferId(dp.getId());
                refer.setType(KnowledgeReferEnum.CHAPTER.getType());
                refers.add(refer);
            });
        }
        herolandKnowledgeReferMapper.deleteByReferId(dp.getId(),KnowledgeReferEnum.CHAPTER.getType());
        if (!CollectionUtils.isEmpty(refers)){
            herolandKnowledgeReferMapper.batchSave(refers);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        heroLandChapterMapper.deleteByPrimaryKey(id);
        List<HerolandChapter> children = Lists.newArrayList();
        getChildren(id, children);
        if (CollectionUtils.isEmpty(children)){
            return true;
        }
        List<Long> ids = children.stream().map(HerolandChapter::getId).collect(Collectors.toList());
        heroLandChapterMapper.batchDeleteByIds(ids);
        ids.add(id);
        herolandKnowledgeReferMapper.batchDeleteByReferIds(ids,KnowledgeReferEnum.CHAPTER.getType());
        return true;
    }

    @Override
    public HerolandChapterDto getById(Long id) {
        HerolandChapter herolandChapter = null;
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        herolandChapter = heroLandChapterMapper.selectByPrimaryKey(id);
        HerolandChapterDto chapterDto = getAdminData(herolandChapter);
        List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(Lists.newArrayList(id), KnowledgeReferEnum.CHAPTER.getType());
        List<Long> knowledgeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
        List<HerolandKnowledgeSimpleDto> dtoList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(knowledgeIds)){
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
            dtoList = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
        }
        chapterDto.setKnowledges(dtoList);
        return chapterDto;
    }

    @Override
    public PageResponse<HerolandChapterDto> pageQuery(HerolandChapterPageRequest request) {
        List<HerolandChapterDto> result = new ArrayList<>();
        PageResponse<HerolandChapterDto> pageResult = new PageResponse<>();
        Page<HerolandChapter> data = PageHelper.startPage(request.getPageIndex(), request.getPageSize(), true).doSelectPage(
                () -> heroLandChapterMapper.getByQuery(request.getGrade(), request.getCourse(), request.getEdition(), request.getContentType(),request.getContent(), request.getParentId()));
        if (!CollectionUtils.isEmpty(data.getResult())){
            List<Long> chapterIds = data.getResult().stream().map(HerolandChapter::getId).collect(Collectors.toList());

            List<HerolandKnowledgeRefer> refers = herolandKnowledgeReferMapper.selectByReferIds(chapterIds, KnowledgeReferEnum.CHAPTER.getType());

            List<Long> knowledgeIds = refers.stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());
            Map<Long, List<HerolandKnowledgeRefer>> chapterMap = refers.stream().collect(Collectors.groupingBy(HerolandKnowledgeRefer::getReferId));

            List<HerolandKnowledgeSimpleDto> dtoList = Lists.newArrayList();
            if (!CollectionUtils.isEmpty(knowledgeIds)){
                List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
                dtoList = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
            }

            Map<Long, List<HerolandKnowledgeSimpleDto>> knowledgeMap = dtoList.stream().collect(Collectors.groupingBy(HerolandKnowledgeSimpleDto::getId));
            data.getResult().stream().forEach(e -> {
                List<HerolandKnowledgeSimpleDto> simpleDtos = Lists.newArrayList();
                HerolandChapterDto chapterDto = getAdminData(e);
                List<HerolandKnowledgeRefer> chapterKnowledge = chapterMap.get(e.getId());
                if (!CollectionUtils.isEmpty(chapterKnowledge)){
                    List<Long> chapterKnowledgeIds =  chapterKnowledge.stream().map(HerolandKnowledgeRefer::getKnowledgeId).collect(Collectors.toList());
                    chapterKnowledgeIds.stream().forEach(id -> simpleDtos.addAll(knowledgeMap.get(id)));
                }
                chapterDto.setKnowledges(simpleDtos);
                result.add(chapterDto);
            });
        }
        pageResult.setItems(result);
        pageResult.setPageSize(data.getPageSize());
        pageResult.setPage(data.getPageNum());
        pageResult.setTotal((int) data.getTotal());
        return pageResult;
    }

    @Override
    public List<HerolandKnowledgeSimpleDto> getChapterKnowledge(HerolandChapterKnowledgeRequest request) {
        ChapterEnum chapterEnum = ChapterEnum.valueOfLevel(request.getChapterType());
        List<HerolandKnowledgeSimpleDto> result = Lists.newArrayList();
        if (Objects.isNull(chapterEnum)){
            return result;
        }
        //如果是章节，根据
        if (chapterEnum.getType().equals(ChapterEnum.ZHANG.getType())){
            HerolandKnowledgeQO qo = new HerolandKnowledgeQO();
            qo.setCourse(request.getCourse());
            qo.setGrade(request.getGrade());
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByQuery(qo);
            result = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
            return result;
        }

        if ((chapterEnum.getType().equals(ChapterEnum.KEJIE.getType()) || chapterEnum.getType().equals(ChapterEnum.JIE.getType()))
                && NumberUtils.nullOrZeroLong(request.getParentId())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        }
        List<Long> knowledgeIds = herolandKnowledgeReferMapper.selectByReferIds(Lists.newArrayList(request.getParentId()), KnowledgeReferEnum.CHAPTER.getType())
                .stream().map(HerolandKnowledgeRefer::getKnowledgeId).distinct().collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(knowledgeIds)){
            List<HerolandKnowledge> herolandKnowledges = herolandKnowledgeMapper.selectByIds(knowledgeIds);
            result = herolandKnowledges.stream().map(this::convert).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<HerolandChapterSimpleDto> getChapterList(HerolandPreChapterRequest request) {

        List<HerolandChapterSimpleDto> list = new ArrayList<>();
        List<HerolandChapter> byQuery = heroLandChapterMapper.getByQuery(request.getGrade(), request.getCourse(), request.getEdition(), request.getContentType(), null, request.getParentId());
        byQuery.stream().forEach(e -> {
            HerolandChapterSimpleDto simpleDto = new HerolandChapterSimpleDto();
            simpleDto.setContent(e.getContent());
            simpleDto.setContentType(e.getContentType());
            simpleDto.setOrder(e.getOrder());
            simpleDto.setId(e.getId());
            list.add(simpleDto);
        });
        return list;
    }

    private HerolandKnowledgeSimpleDto convert(HerolandKnowledge knowledge){
        HerolandKnowledgeSimpleDto dto = new HerolandKnowledgeSimpleDto();
        dto.setKnowledge(knowledge.getKnowledge());
        dto.setId(knowledge.getId());
        return dto;
    }


    private void getChildren(Long parentId, List<HerolandChapter> children){
        List<HerolandChapter> list = heroLandChapterMapper.getByParent(parentId);
        if (!CollectionUtils.isEmpty(list)){
            children.addAll(list);
            for (HerolandChapter chapter : list){
                getChildren(chapter.getId(), children);
            }
        }
    }

    private HerolandChapterDto getAdminData(HerolandChapter chapter){
        HerolandChapterDto dto = new HerolandChapterDto();
        dto.setCourse(chapter.getCourse());
        dto.setEdition(chapter.getEdition());
        dto.setGrade(chapter.getGrade());
        dto.setId(chapter.getId());
        List<String> keys = Lists.newArrayList();
        keys.add(chapter.getCourse());
        keys.add(chapter.getGrade());
        keys.add(chapter.getEdition());
        List<HerolandBasicDataDP> dictInfoByKeys = heroLandAdminService.getDictInfoByKeys(keys);
        Map<String, List<HerolandBasicDataDP>> keyMap = dictInfoByKeys.stream().collect(Collectors.groupingBy(HerolandBasicDataDP::getDictKey));
        if (keyMap.containsKey(chapter.getCourse())){
            dto.setCourseName(keyMap.get(chapter.getCourse()).get(0).getDictValue());
        }
        if (keyMap.containsKey(chapter.getEdition())){
            dto.setEditionName(keyMap.get(chapter.getEdition()).get(0).getDictValue());
        }
        if (keyMap.containsKey(chapter.getGrade())){
            dto.setGradeName(keyMap.get(chapter.getGrade()).get(0).getDictValue());
        }
        dto.setContent(chapter.getContent());
        dto.setContentType(chapter.getContentType());
        dto.setOrder(chapter.getOrder());
        return dto;
    }
}
