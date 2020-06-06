//package com.heroland.competition.config;
//
//import com.alibaba.fastjson.JSON;
//import com.alijk.bq.context.UserHolder;
//import com.alijk.bq.context.UserHolderContext;
//import com.alijk.bq.util.IPUtils;
//import com.alijk.bqcommon.search.entity.EsEntity;
//import com.alijk.bqcommon.search.store.ElasticSearchStore;
//import com.alijk.bqhealth.cloud.dal.qo.BaseQO;
//import com.alijk.bqhealth.cloud.vo.BaseVO;
//import com.alijk.bqhealth.cloud.vo.HealthCloudMainIndexLogVO;
//import com.alijk.bqhealth.sso.exception.PermissionException;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Field;
//import java.util.*;
//import java.util.stream.Collectors;
//
//
///**
// * 全局处理
// *
// * @author wangkai
// */
//@Aspect
//@Component
//public class GlobalHandlerAspect {
//    private Logger logger = LoggerFactory.getLogger(GlobalHandlerAspect.class);
//
//
//    @Resource
//    private ElasticSearchStore elasticSearchStore;
//
//
//    @Pointcut(value = "execution(public * com.alijk.bqhealth.cloud.controller.*.*(..))")
//    public void executeController() {
//    }
//
//
//    @Around(value = "executeController()")
//    public Object preSet(ProceedingJoinPoint joinPoint) {
//        Object proceed;
//
//        HealthCloudMainIndexLogVO indexLogVO = new HealthCloudMainIndexLogVO();
//        String id = "";
//        try {
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            Object[] args = joinPoint.getArgs();
//            SystemLog log = signature.getMethod().getAnnotation(SystemLog.class);
//            UserHolder userInfo = UserHolderContext.getUser();
//            for (Object arg : args) {
//
//                Object o = null;
//                try {
//
//                    Field[] declaredFields = arg.getClass().getDeclaredFields();
//                    for (Field declaredField : declaredFields) {
//
//                        declaredField.setAccessible(true);
//                        Object v = declaredField.get(arg);
//                        if (arg instanceof BaseQO) {
//                            if ((v instanceof String) && StringUtils.isBlank((String) v)) {
//                                declaredField.set(arg, null);
//                            }
//                        }
//                        if ("orgCode".equalsIgnoreCase(declaredField.getName())) {
//                            o = declaredField.get(arg);
//                        }
//                    }
//
//                } catch (Exception ignored) {
//                }
//
//                if (arg instanceof BaseVO) {
//
//                    //
//                    if (signature.getMethod().getName().contains("update") || signature.getMethod().getName().contains("delete") || signature.getMethod().getName().contains("add")) {
//                        ((BaseVO) arg).setModifier(userInfo.getWorkId());
//                    } else if (signature.getMethod().getName().contains("save") || signature.getMethod().getName().contains("add")) {
//                        ((BaseVO) arg).setCreator(userInfo.getWorkId());
//                    }
//                }
//
//
//            }
//
//            // 记录操作日志
//
//            if (log != null) {
//
//                List<Object> objects = Arrays.asList(args);
//
//                List<Object> logArgs = objects.stream().filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))).collect(Collectors.toList());
//                List<EsEntity> logs = new ArrayList<>();
//                EsEntity<Map> esEntity = new EsEntity<>();
//                indexLogVO.setContent(log.content());
//                indexLogVO.setCreator(userInfo.getWorkId());
//                indexLogVO.setGmtCreate(new Date());
//                indexLogVO.setIp(IPUtils.getIp());
//                indexLogVO.setType(log.type().getType());
//                indexLogVO.setRequest(JSON.toJSONString(logArgs));
//                indexLogVO.setOrgCode(userInfo.getOrgCode());
//                indexLogVO.setOrgGroup(userInfo.getOrgGroup());
//                indexLogVO.setUserCode(userInfo.getUserCode());
//                id = UUID.randomUUID().toString();
//                indexLogVO.setId(id);
//                esEntity.setId(id);
//                Map map = JSON.parseObject(JSON.toJSONString(indexLogVO), Map.class);
//                esEntity.setData(map);
//                logs.add(esEntity);
//                try {
//                    elasticSearchStore.insertBatchAsync(EsIndexConstant.LOG_INDEX, logs);
//                } catch (Exception e) {
//                    logger.error("保存es日志错误", e);
//                }
//
//
//            }
//            proceed = joinPoint.proceed();
//
//            if (log != null) {
//                List<EsEntity<Map<String, Object>>> logs = new ArrayList<>();
//                EsEntity<Map<String, Object>> esEntity = new EsEntity<>();
//                indexLogVO.setResponse(JSON.toJSONString(proceed));
//                esEntity.setId(indexLogVO.getId());
//                esEntity.setData(JSON.parseObject(JSON.toJSONString(indexLogVO), Map.class));
//                logs.add(esEntity);
//                try {
//                    elasticSearchStore.updateById(EsIndexConstant.LOG_INDEX, logs);
//                } catch (Exception e) {
//                    logger.error("更新es日志错误", e);
//                }
//
//
//            }
//        } catch (Throwable throwable) {
//            logger.error("error", throwable);
//            if (throwable instanceof PermissionException) {
//                throw new PermissionException(throwable.getMessage());
//            }
//            throw new RuntimeException("系统错误");
//        }
//        return proceed;
//    }
//
//}
