//package com.alijk.bqhealth.cloud.spark;
//
//
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;
//
//import java.util.ArrayList;
//import java.util.Properties;
//
///**
// * bqhealth-cloud
// *
// * @author wangkai
// * @date 2019/12/4
// */
//
//public class MainIndexSparkService {
//
//
//    public static void main(String[] args) {
//        SparkConf tests = new SparkConf().setMaster("spark://192.168.1.127:7077").setAppName("tests");
//        JavaSparkContext javaSparkContext = new JavaSparkContext(tests);
//        javaSparkContext.master();
//        javaSparkContext.appName();
//        Properties properties = new Properties();
//        properties.setProperty("user","bqhealthcloud");
//        properties.setProperty("password","bqcloud@bq0330");
//        SparkSession spark=SparkSession.builder().config(tests).getOrCreate();
//
//        Dataset<Row> bqhealth_cloud_db = spark.read().jdbc("jdbc:mysql://rm-bp1755ydatm21f766eo.mysql.rds.aliyuncs.com:3306/bqhealth_cloud_db?serverTimezone=GMT&useSSL=false&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true",
//                "bqhealth_cloud_cdata_drug_list", properties);
//
//        bqhealth_cloud_db.printSchema();
//        bqhealth_cloud_db.createOrReplaceTempView("bqhealth_cloud_cdata_drug_list");
//        Dataset<Row> sql = spark.sql("select id,drug_sort  from (select *,row_number() over (partition by drug_sort  ORDER BY id) num from bqhealth_cloud_cdata_drug_list) t where t.num=1 ");
//        Dataset<Row> a = bqhealth_cloud_db.as("a");
//
//        Dataset<Row> b = bqhealth_cloud_db.as("b");
//        javaSparkContext.parallelize(new ArrayList<>());
//        Dataset<Row> join = a.select("*").dropDuplicates("drug_sort").
//                join(b, bqhealth_cloud_db.col("id").notEqual(bqhealth_cloud_db.col("id")).
//                        and(bqhealth_cloud_db.col("drug_sort").equalTo(bqhealth_cloud_db.col("drug_sort"))));
////        join.dropDuplicates().show();
//        join.show();
//        System.out.println(join.count());
////        List<Row> rows1 = join.dropDuplicates().collectAsList();
////        List<Row> rows = join.collectAsList();
////        System.out.println(asrows);
////        join.agg()
//
//        spark.close();
//
//
//    }
//}
