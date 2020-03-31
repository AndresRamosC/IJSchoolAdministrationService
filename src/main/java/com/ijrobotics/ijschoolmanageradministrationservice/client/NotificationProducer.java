package com.ijrobotics.ijschoolmanageradministrationservice.client;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.enumeration.NotificationType;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class NotificationProducer  {
    Logger logger = LoggerFactory.getLogger(NotificationProducer.class.getName());
    private KafkaProducer<String, String> producer;
    public NotificationProducer() {
        this.run();
    }
    public void run() {
        logger.info("Setup");
        logger.info("*************PRODUCER READY*************");
        //Create a kafka Producer
        this.producer = createKafkaProducer();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Stop app");
            logger.info("Clossing Producer");
            producer.close();
            logger.info("done");
        }));

        logger.info("End of app");
    }

    public void SendNotification(String body, NotificationType notificationType){
        logger.info(body);
        logger.info("******************"+producer);
        producer.send(new ProducerRecord<>("Notifications", "attendance", body+","+notificationType), (recordMetadata, e) -> {
            if (e != null) {
                logger.error("Something bad happened", e);
            }
        });
    }

    public KafkaProducer<String, String> createKafkaProducer() {
//Create Producer Properties
        Properties properties = new Properties();
        String bootstrapServer = "127.0.0.1:9092";
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Create a safe producer

        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "3");

        //High throughput producer (at the expense of a bit of latency and CPU usage)
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG,"snappy");
        properties.setProperty(ProducerConfig.LINGER_MS_CONFIG,"20");
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024));

        //create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }

}
