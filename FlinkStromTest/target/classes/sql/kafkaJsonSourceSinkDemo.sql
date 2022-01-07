CREATE TABLE order_log(
orderId STRING,
bookId STRING,
bookName STRING,
bookType STRING,
bookNum INT ,
bookPrice FLOAT ,
bookPress STRING,
Id INT
) WITH (
      'connector.type' = 'kafka',
      'connector.version' = 'universal',
      'connector.topic' = 'user_behavior',
      'connector.properties.zookeeper.connect' = '192.168.17.141:2181,192.168.17.142:2181,192.168.17.143:2181',
      'connector.properties.bootstrap.servers' = '192.168.17.141:9092,192.168.17.142:9092,192.168.17.143:9092',
      'connector.startup-mode' = 'latest-offset',
      'format.type' = 'json'
      );