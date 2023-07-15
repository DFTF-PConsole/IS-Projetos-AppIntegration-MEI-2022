package uc.mei.is.compute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class ComputeFewMetrics {
    private final static String standardWeatherTopic = "standardWeather";

    private final static String weatherAlertsTopic = "weatherAlerts";

    private final static String[] resultTopics = {
            "req1",
            "req2",
            "req3",
            "req4",
            "req5",
            "req6",
            "req7",
            "req8",
            "req9",
            "req10",
            "req11"
    };;

    /**
     * Weather Station	| Location
     * Coimbra			| Pinhal de Marrocos
     * Coimbra			| Norton de Matos
     * Lisboa			| Bairro Alto
     * Lisboa			| Alfama
     */
    private final static String DBInfoTopics = "DBInfo";

    private final static String kafkaBrokerAddress = "localhost:29092,localhost:29093,localhost:29094";

    private static boolean debugActive = true;

    private final static String appID = "ComputeFewMetrics";

    public static void main(String[] args) {
        if(isDebugActive()) {
            System.out.println("APP \"" + ComputeFewMetrics.appID + "\" - Booting...");
        }

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, ComputeFewMetrics.appID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ComputeFewMetrics.kafkaBrokerAddress);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final ArrayList<StreamsBuilder> streamsBuilders = new ArrayList<>(11);
        final ArrayList<Topology> topologies = new ArrayList<>(11);
        final ArrayList<KafkaStreams> kafkaStreamsList = new ArrayList<>(11);

        for (int i = 1 ; i <= 11 ; i++) {
            streamsBuilders.add(new StreamsBuilder());
        }

        if(isDebugActive()) {
            System.out.println("APP \"" + ComputeFewMetrics.appID + "\" (" + ComputeFewMetrics.standardWeatherTopic + ") - Req. 1...");
        }
        // REQ 1: Count temperature readings of standard weather events per weather station
        streamsBuilders.get(0).stream(ComputeFewMetrics.standardWeatherTopic)
                .groupBy((key, value) -> key)
                .count()
                .toStream()
                .map((key, value) -> {
                    String json = 
                            "{" +
                                "\"schema\":{" +
                                    "\"type\":\"struct\"," +
                                    "\"fields\":[" +
                                            "{" +
                                            "\"type\":\"int32\"," +
                                            "\"optional\":false," +
                                            "\"field\":\"id\"" +
                                        "}," +
                                            "{" +
                                            "\"type\":\"string\"," +
                                            "\"optional\":false," +
                                            "\"field\":\"weather_station\"" +
                                        "}," +
                                            "{" +
                                            "\"type\":\"int32\"," +
                                            "\"optional\":false," +
                                            "\"field\":\"number_temperature_readings\"" +
                                        "}," +
                                            "{" +
                                            "\"type\":\"int64\"," +
                                            "\"optional\":false  ," +
                                            "\"name\":\"org.apache.kafka.connect.data.Timestamp\"," +
                                            "\"version\":1," +
                                            "\"field\":\"created_at\"" +
                                        "}" +
                                    "]," +
                                    "\"optional\":false" +
                                "}," +
                                "\"payload\":{" +
                                    "\"weather_station\":\"" + key + "\"," +
                                    "\"number_temperature_readings\":" + value +
                                "}" +
                            "}";
                    if(isDebugActive()) {
                        System.out.println("APP \"" + ComputeFewMetrics.appID + "\" [REQ. 1] weather_station: " + key + " | number_temperature_readings: " + value + " - [" + ComputeFewMetrics.resultTopics[0] + "] Key: " + "null" + " | Value: " + json);
                    }
                    return KeyValue.pair("null", json);
                })
                .to(ComputeFewMetrics.resultTopics[0], Produced.with(Serdes.String(), Serdes.String()));


        // REQ 2: Count temperature readings of standard weather events per location
        streamsBuilders.get(1).stream(ComputeFewMetrics.standardWeatherTopic)
                .map((key, value) -> {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNode = null;
                    try {
                        jsonNode = objectMapper.readTree(String.valueOf(value));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        System.exit(-1);
                    }
                    String location = jsonNode.get("location").asText("null");

                    return KeyValue.pair(location, "null");
                })
                .groupBy((key, value) -> key)
                .count()
                .toStream()
                .map((key, value) -> {
                    String json =
                            "{" +
                                "\"schema\":{" +
                                    "\"type\":\"struct\"," +
                                    "\"fields\":[" +
                                            "{" +
                                            "\"type\":\"int32\"," +
                                            "\"optional\":false," +
                                            "\"field\":\"id\"" +
                                        "}," +
                                            "{" +
                                            "\"type\":\"string\"," +
                                            "\"optional\":false," +
                                            "\"field\":\"location\"" +
                                        "}," +
                                            "{" +
                                            "\"type\":\"int32\"," +
                                            "\"optional\":false," +
                                            "\"field\":\"number_temperature_readings\"" +
                                        "}," +
                                            "{" +
                                            "\"type\":\"int64\"," +
                                            "\"optional\":false  ," +
                                            "\"name\":\"org.apache.kafka.connect.data.Timestamp\"," +
                                            "\"version\":1," +
                                            "\"field\":\"created_at\"" +
                                        "}" +
                                    "]," +
                                    "\"optional\":false" +
                                "}," +
                                "\"payload\":{" +
                                    "\"location\":\"" + key + "\"," +
                                    "\"number_temperature_readings\":" + value +
                                "}" +
                            "}";
                    if(isDebugActive()) {
                        System.out.println("APP \"" + ComputeFewMetrics.appID + "\" [REQ. 2] location: " + key + " | number_temperature_readings: " + value + " - [" + ComputeFewMetrics.resultTopics[1] + "] Key: " + "null" + " | Value: " + json);
                    }
                    return KeyValue.pair("null", json);
                })
                .to(ComputeFewMetrics.resultTopics[1], Produced.with(Serdes.String(), Serdes.String()));


        // TODO REQ 3: Get minimum and maximum temperature per weather station
        //KStream<String, Double> sourceReq3 = streamsBuilders.get(2).stream(ComputeFewMetrics.standardWeatherTopic);


        // TODO REQ 4: Get minimum and maximum temperature per location (Students should compute these values in Fahrenheit)
        //KStream<String, Double> sourceReq4 = streamsBuilders.get(3).stream(ComputeFewMetrics.standardWeatherTopic);


        // TODO REQ 5: Count the total number of alerts per weather station
        //KStream<String, Double> sourceReq5 = streamsBuilders.get(4).stream(ComputeFewMetrics.weatherAlertsTopic);


        // TODO REQ 6: Count the total alerts per type

        // TODO REQ 7: Get minimum temperature of weather stations with red alert events

        // TODO REQ 8: Get maximum temperature of each location of alert events for the last hour (students are allowed to define a different value for the time window)

        // TODO REQ 9: Get minimum temperature per weather station in red alert zones

        // TODO REQ 10: Get the average temperature per weather station

        // TODO REQ 11: Get the average temperature of weather stations with red alert events for the last hour (students are allowed to define a different value for the time window)


        if(isDebugActive()) {
            System.out.println("APP \"" + ComputeFewMetrics.appID + "\" - Building...");
        }
        for (StreamsBuilder streamsBuilder : streamsBuilders) {
            Topology topology = streamsBuilder.build();
            topologies.add(topology);
            kafkaStreamsList.add(new KafkaStreams(topology, properties));
        }


        // catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread(ComputeFewMetrics.appID + "-" + "Shutdown") {
            @Override
            public void run() {
                if(isDebugActive()) {
                    System.out.println("THREAD SHUTDOWN (" + ComputeFewMetrics.appID + ") - Control-C");
                }

                int i = 1;
                for (KafkaStreams kafkaStreams : kafkaStreamsList) {
                    if(isDebugActive()) {
                        System.out.println("APP \"" + ComputeFewMetrics.appID + "\" - Kafka stream requirement " + i++ + " closed");
                    }
                    kafkaStreams.close();
                }

                countDownLatch.countDown();
            }
        });

        try {

            int i = 1;
            for (KafkaStreams kafkaStreams : kafkaStreamsList) {

                if(isDebugActive()) {
                    System.out.println("APP \"" + ComputeFewMetrics.appID + "\" - Kafka stream requirement " + i++ + " started");
                }
                kafkaStreams.start();

            }

            if(isDebugActive()) {
                System.out.println("APP \"" + ComputeFewMetrics.appID + "\" - Waiting...");
            }
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if(isDebugActive()) {
            System.out.println("APP \"" + ComputeFewMetrics.appID + "\" - Finished");
        }

        System.exit(0);
    }


    public static boolean isDebugActive() {
        return debugActive;
    }

    public static void setDebugActive(boolean debugActive) {
        ComputeFewMetrics.debugActive = debugActive;
    }

}
