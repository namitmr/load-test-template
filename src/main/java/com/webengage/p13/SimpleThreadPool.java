package com.webengage.p13;

import com.expeval.tokenizer.Context;
import com.expeval.tokens.BinaryOperator;
import com.expeval.tokens.Function;
import com.expeval.tokens.Var;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;

import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.apache.http.annotation.Immutable;
import org.json.JSONObject;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;

import java.awt.*;
import java.net.ConnectException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import com.expeval.*;

public class SimpleThreadPool {
    public static void main(String[] args) throws Exception {
        Context.getDefault().registerFunction(new Function("STARTS_WITH") {
            @Override
            public Object onEvaluation(List<Object> list) {
                if (list.size() > 2) {
                    return false;
                } else {
//                    if(list == null || list.size() == 0) {
////                        System.out.println("HAHAHA: " + (list != null ? list.toString() : "null"));
//                        return false;
//                    } else {
//                        System.out.println("HAHAHA: " + (list != null ? list.toString() : "null"));
                        return list.get(0).toString().startsWith(list.get(1).toString());
//                    }
                }
            }
        });

        Context.getDefault().registerFunction(new Function("ENDS_WITH") {
            @Override
            public Object onEvaluation(List<Object> list) {
                if (list.size() > 2) {
                    return false;
                } else
                    return list.get(0).toString().endsWith(list.get(1).toString());
            }
        });


        Context.getDefault().registerBinaryOperator(new BinaryOperator("_", BinaryOperator.EQ.getPrecedence() + 1) {
            @Override
            public Object onEvaluation(Object o, Object o1) {
                return ((Map<String, Object>) o).get(o1.toString());
            }
        });




        ExecutorService executor = Executors.newFixedThreadPool(5);
//        ExecutorService executor = Executors.newWorkStealingPool(20);
//        List<Callable<Void>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            Runnable myRunnable = new MyRunnable(i);

            executor.submit(myRunnable);
//            Thread t = new Thread(myRunnable);
//            t.start();
//            t.join();
        }

        }


    }


class MyRunnable implements Runnable {

    private int i;
    public MyRunnable(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        Map<String,Object> m = new ConcurrentHashMap<>();

        Var v = new Var("event"+i);
        v.setValue(m);
        Context.getDefault().registerVar(v);
        Map<String,Object> m2 = new ConcurrentHashMap<>();
        Map<String,Object> lc_map = new ConcurrentHashMap<>();
        Map<Object,String> expression_map = new ConcurrentHashMap<>();
        String arr[] = {"custom","system","application"};
        String eventName[] = {"push_click","sms_click","push_rejected"};
        String lc[] = {"12345","34567","56789"};



        lc_map.put("12345", expression_map);
        lc_map.put("34567",expression_map);
        lc_map.put("56789",expression_map);


        Expression e = Expression.parse("event"+i+" _ \"category\" == \"custom\"");
        Expression e1 = Expression.parse("event"+i+" _ \"system_data\" _ \"scopeId\" != null");
        Expression e2 = Expression.parse("STARTS_WITH(event"+i+" _ \"event_name\", \"push\")");
        Expression e3 = Expression.parse("ENDS_WITH(event"+i+" _ \"event_name\", \"click\")");

        expression_map.put(e,"http://google.com");
        expression_map.put(e1,"http://googledoodle.com");
        expression_map.put(e2,"http://fuckoff.com");
        expression_map.put(e3,"http://damnson.com");


//        System.out.println(e.eval());
        String lcode="";
        int count = 0;
        for (int i = 0; i < 1000000; i++) {

            lcode = lc[(int) (Math.random() * (3 - 0)) + 0];
            m.put("category", arr[(int) (Math.random() * (3 - 0)) + 0]);
            m.put("event_name", eventName[(int) (Math.random() * (3 - 0)) + 0]);
            m.put("system_data", m2);
            if(i % ((int) (Math.random() * (3 - 2)) + 2) == 0) {
                m2.put("scopeId", "12345");
            }
            for (Map.Entry<Object, String> entry : ((Map<Object, String>)lc_map.get(lcode)).entrySet()){
                ((Expression)entry.getKey()).eval();
                count++;
            }
            m.clear();
            m2.clear();

        }
        System.out.println(count+" total time :"+(start-System.currentTimeMillis()));
    }
}
////        //MyRunnable r = new MyRunnable();
//        Runnable worker = new MyRunnable();
//        System.out.println(new Timestamp(new java.util.Date().getTime()));
//        ExecutorService executor = Executors.newFixedThreadPool(8000);
//        for (int i = 0; i < 1000; i++) {
//            executor.execute(worker);
//        }
//        executor.shutdown();
////        while (!executor.isTerminated()) {
////        }
////        String input = "wow, so example: www.namsexception.in ";
////        UrlDetector parser = new UrlDetector("hello this is a url http://requestb.in/106l1my1?p1=val&name={{user['system']['first_name']|urlencode}}&spchar=nothing Linkedin.com www.namsexception.in t.co?a=b.&ctaId=123 namit@webklipper.com ftp://abc.com,", UrlDetectorOptions.Default);
////        List<Url> found = parser.detect();
////
////        for(Url url : found) {
////            System.out.println("url: " + url.toString());
////            System.out.println("Host: " + url.getHost());
////            System.out.println("Path: " + url.getPath());
////        }
//
//
////        HttpResponse<String> response = Unirest.post("https://lambda.webengage.com/survey_lambda_http?eventType=SURVEY_RESPONSE&licenseCode=82617417&secret=b58d245f908ab8368d2b7713afc62b67")
////                .header("content-type", "application/json")
////                .header("cache-control", "no-cache")
////                .header("postman-token", "2718394c-84e4-a3aa-2816-91857a4d5061")
////                .asString();
//
//        HttpRequestWithBody res = Unirest.post("http://smssender-1636434612.us-west-2.elb.amazonaws.com/sms-messages/create").header("content-type", "application/json");
//        String response = requestBodyEntity.asString().getBody();
//        System.out.println(response);
//
//    }
////    public static String fuckall(String x){
////        RequestBodyEntity requestBodyEntity = res.body("");
////        return x;
////    }
////    public static String noFucks(){
////        return "Fuxk off";
////    }
//
//    static class GetRunnable {
//        private static final MyRunnable r = new MyRunnable();
//
//        public static MyRunnable get() {
//            return r;
//        }
//    }
////
////    static class MyRunnable implements Runnable {
////        @Override
////        public void run() {
////            try {
//////                HttpResponse<String> response = Unirest.post("http://localhost:3000/templates/58adc698/nams1")
////                        .header("content-type", "application/json")
////                        .header("authorization", "Basic d2ViZW5nYWdlOndlYmVuZ2FnZQ==")
////                        .header("cache-control", "no-cache")
////                        .header("postman-token", "095aeef0-b56d-4865-a66c-92eea915533d")
////                        .body("{\n    \"users\": [{\n        \"luid\": \"00000154-f1b0-4b7e-8632-a08677bedbd8\",\n        \"cuid\": \"raunak\",\n        \"communicationId\": \"return as is\",\n        \"context\": {\n            \"user\": {\n                \"we_run_data\": {\n                    \"WE_WK_LICENSE_CODE\":\"lc1\",\n\t\t\t\t\t\"WE_WK_LUID\":\"luid1\",\n\t \t\t\t\t\"WE_WK_CUID\":\"cuid1\",\n\t\t\t\t\t\"WE_WK_EMAIL_ID\":\"email_id1\", \n\t\t\t\t\t\"WE_WK_EXPERIMENT_ID\":\"expid1\",\n\t\t\t\t\t\"WE_WK_VARIATION_ID\":\"varid1\",\n\t\t\t\t\t\"WE_WK_SCOPE\":\"scope1\",\n\t\t\t\t\t\"WE_WK_JOURNEY_ID\":\"jid1\"\n                }\n            }\n        }\n    },\n    {\n        \"luid\": \"00000154-f1b0-4b7e-8632-a08677bedbd8\",\n        \"cuid\": \"raunak\",\n        \"communicationId\": \"return as is\",\n        \"context\": {\n            \"user\": {\n                \"we_run_data\": {\n                    \"WE_WK_LICENSE_CODE\":\"lc2\",\n\t\t\t\t\t\"WE_WK_LUID\":\"luid2\",\n\t \t\t\t\t\"WE_WK_CUID\":\"cuid2\",\n\t\t\t\t\t\"WE_WK_EMAIL_ID\":\"email_id2\", \n\t\t\t\t\t\"WE_WK_EXPERIMENT_ID\":\"expid2\",\n\t\t\t\t\t\"WE_WK_VARIATION_ID\":\"varid2\",\n\t\t\t\t\t\"WE_WK_SCOPE\":\"scope2\",\n\t\t\t\t\t\"WE_WK_JOURNEY_ID\":\"jid2\"\n                }\n            }\n        }\n    },\n    {\n        \"luid\": \"00000154-f1b0-4b7e-8632-a08677bedbd8\",\n        \"cuid\": \"raunak\",\n        \"communicationId\": \"return as is\",\n        \"context\": {\n            \"user\": {\n                \"we_run_data\": {\n                    \"WE_WK_LICENSE_CODE\":\"lc3\",\n\t\t\t\t\t\"WE_WK_LUID\":\"luid3\",\n\t \t\t\t\t\"WE_WK_CUID\":\"cuid3\",\n\t\t\t\t\t\"WE_WK_EMAIL_ID\":\"email_id3\", \n\t\t\t\t\t\"WE_WK_EXPERIMENT_ID\":\"expid3\",\n\t\t\t\t\t\"WE_WK_VARIATION_ID\":\"varid3\",\n\t\t\t\t\t\"WE_WK_SCOPE\":\"scope3\",\n\t\t\t\t\t\"WE_WK_JOURNEY_ID\":\"jid3\"\n                }\n            }\n        }\n    }]\n}")
////                        .asString();
////            } catch (UnirestException e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////}
//////    class Test {
//////        public static  void main(String args[]){
//////
//////            RetryPolicy retryPolicy = new RetryPolicy()
//////                    .retryOn(ConnectException.class)
//////                    .withDelay(1, TimeUnit.SECONDS)
//////                    .withMaxRetries(3);
//////          Failsafe.with(retryPolicy).get(() -> SimpleThreadPool.fuckall("hi"));
//////
//////        }
//////
//////    }
