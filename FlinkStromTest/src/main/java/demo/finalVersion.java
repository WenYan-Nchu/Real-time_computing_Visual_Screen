package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import redis.JRedisUtil;

import java.math.BigDecimal;
import java.util.Properties;

public class finalVersion {
    public static JRedisUtil redisUtil=new JRedisUtil("localhost",6379);
    public static void main(String[] args) {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//        env.setParallelism(3);

        //连接kafka
        Properties properties=new Properties();
        properties.setProperty("bootstrap.servers","192.168.17.141:9092,192.168.17.142:9092,192.168.17.143:9092");
        properties.setProperty("group.id","consumer-group");
        properties.setProperty("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");
        DataStream<String> stream1=env.addSource( new FlinkKafkaConsumer010<String>(
                "localOrder", new SimpleStringSchema(), properties)).setParallelism(2);
        DataStream<String> stream2=env.addSource( new FlinkKafkaConsumer010<String>(
                "localOrderdetail", new SimpleStringSchema(), properties)).setParallelism(2);

        //数据处理
        stream1.map(line->{
            try {
                JSONObject JsonObject= JSON.parseObject(line).getJSONArray("data").getJSONObject(0);
//            String orderId=JsonObject.getString("order_id");
//            String bookId=JsonObject.getString("book_id");
                String bookName=JsonObject.getString("book_name");
                int bookNum=JsonObject.getIntValue("book_num");
                String bookType=JsonObject.getString("book_type");
//            Float bookPrice=JsonObject.getFloatValue("book_price");
                String bookPress=JsonObject.getString("book_press");
//            Integer id=JsonObject.getIntValue("id");

                //类型排行榜
                redisUtil.zincrby("typeRank", bookNum,bookType);
                //图书排行榜
                redisUtil.zincrby("bookRank", bookNum,bookName);
                //出版社排行榜
                redisUtil.zincrby("pressRank",bookNum,bookPress);
            } catch (Exception e) {
                redisUtil.incr("Error");
            }
            return 0;
        }).setParallelism(4);

        stream2.map(line->{
            try {
                //订单数量
                redisUtil.incr("totalCount");
                JSONObject JsonObject= JSON.parseObject(line).getJSONArray("data").getJSONObject(0);
//                String orderDetailId=JsonObject.getString("orderdetailid");
//                String orderId=JsonObject.getString("orderid");
//            String sender=JsonObject.getString("sender");
//            String sendPlace=JsonObject.getString("sendplace");
                String recipient=JsonObject.getString("recipient");
                String receivePlace=JsonObject.getString("receiveplace").substring(0,2);
                String methodOfPayment=JsonObject.getString("methodofpayment");
//            String payTime=JsonObject.getString("paytime");
                Float freight=JsonObject.getFloatValue("freight");
                Float total=JsonObject.getFloatValue("total");

                //支付方式
                redisUtil.hincrby("mofNum",methodOfPayment, 1L);
                //地点
                if(receivePlace.equals("黑龙")){
                    String place="黑龙江";
                    redisUtil.zincrby("placeNum",1,place);
                }else if(receivePlace.equals("内蒙")){
                    String place="内蒙古";
                    redisUtil.zincrby("placeNum",1,place);
                }else{
                    redisUtil.zincrby("placeNum",1,receivePlace);
                }
                //下单客户数
                redisUtil.sadd("consumerNum",recipient);
                //销售总金额
                BigDecimal bp = new BigDecimal(String.valueOf(total-freight));
                redisUtil.incrByFloat("totalPrice",bp.doubleValue());
            } catch (Exception e) {
                redisUtil.incr("Error");
            }
            return 0;
        }).setParallelism(4);


        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
