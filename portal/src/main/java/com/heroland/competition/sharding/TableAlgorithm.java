//package com.alijk.bqhealth.cloud.sharding;
//
//import io.shardingjdbc.core.api.algorithm.sharding.PreciseShardingValue;
//import io.shardingjdbc.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author sujiaxin
// * @date 2019.08.08
// **/
//public class TableAlgorithm implements PreciseShardingAlgorithm<String> {
//
//
//    private static final Map<String,Integer> TABLES_COUNT=new HashMap<>();
//    {
//        TABLES_COUNT.put("bqhealth_cloud_test_report_patient",9);
//        TABLES_COUNT.put("bqhealth_cloud_test_report_result",20);
//        TABLES_COUNT.put("bqhealth_cloud_test_report_order",10);
//        TABLES_COUNT.put("bqhealth_cloud_test_app_report",9);
//        TABLES_COUNT.put("bqhealth_cloud_total_patient",3);
//        TABLES_COUNT.put("bqhealth_cloud_case_history_record",5);
//        TABLES_COUNT.put("bqhealth_cloud_case_record_html",3);
//    }
//
//    @Override
//    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
//        Integer count = TABLES_COUNT.get(preciseShardingValue.getLogicTableName());
//
//        int hashVal = 0;
////        hashVal = Hashing.hmacMd5(preciseShardingValue.getValue().getBytes(StandardCharsets.UTF_8)).hashString();
////        int value = BigDecimal.valueOf(hashVal)
////                .remainder(BigDecimal.valueOf(count))
////                .abs()
//////                .intValue();
////        System.out.println("分表----"+preciseShardingValue.getLogicTableName() + "_" +
////                value);
////
////        return preciseShardingValue.getLogicTableName() + "_" +
////                value;
//        return null;
//    }
//}
