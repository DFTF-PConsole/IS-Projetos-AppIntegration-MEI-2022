package uc.mei.is.weather;

import java.time.Duration;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;


public class WeatherStationSimulator {
    public final static String[] typeAlerts = {"red", "green"};

    private final static String standardWeatherTopic = "standardWeather";

    private final static String weatherAlertsTopic = "weatherAlerts";

    private final static String resultsTopics = "results";

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

    private final String weatherStation;

    private final static String appID = "weatherStation";

    private final String[] locations;

    /**
     * @param weatherStation
     *      id/main location
     * @param locations
     *      list locations
     */
    WeatherStationSimulator(String weatherStation, String[] locations){
        this.weatherStation = weatherStation;
        this.locations = locations;
    }

    /**
     * Create Weather Stations (+ locations) present in the database (DBInfo topics)
     */
    public static void main(String[] args) throws JsonProcessingException {
        if(isDebugActive()) {
            System.out.println("APP \"" + WeatherStationSimulator.appID + "\" - Booting...");
        }

        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, WeatherStationSimulator.appID);
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, WeatherStationSimulator.kafkaBrokerAddress);
        properties.setProperty("zookeeper.connect", "localhost:32181");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-weatherStation");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumerDBInfoTopics = new KafkaConsumer<>(properties);

        consumerDBInfoTopics.subscribe(Collections.singleton(WeatherStationSimulator.DBInfoTopics));

        if(isDebugActive()) {
            System.out.println("APP \"" + WeatherStationSimulator.appID + "\" (" + WeatherStationSimulator.DBInfoTopics + ") - Fetching data...");
        }
        ConsumerRecords<String, String> records = consumerDBInfoTopics.poll(Duration.ofMillis(30000));

        HashMap<String, HashSet<String>> weatherStation_locations = new HashMap<>();

        for (ConsumerRecord<String, String> record : records) {

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(record.value());
            String key = jsonNode.get("payload").get("weather_station").asText("null");
            String value = jsonNode.get("payload").get("location").asText("null");

            if(isDebugActive()) {
                System.out.println("INITIAL DATA WEATHER STATIONS (DBInfoTopics) - Weather Station: " + key + " | Location: " + value);
            }

            if(weatherStation_locations.containsKey(key)) {
                weatherStation_locations.get(key).add(value);
            } else {
                weatherStation_locations.put(key, new HashSet<String>(Collections.singleton(value)));
            }
        }

        ArrayList<Thread> weatherStationSimulatorList = new ArrayList<Thread>();

        for (String key : weatherStation_locations.keySet()) {
            String[] listinha = new String[weatherStation_locations.get(key).size()];
            weatherStation_locations.get(key).toArray(listinha);
            Thread thread = new Thread(new WeatherStationSimulator(key, listinha)::run);
            weatherStationSimulatorList.add(thread);
            thread.start();
        }

        try {
            for (Thread thread : weatherStationSimulatorList) {
                thread.join();
            }
        } catch (InterruptedException e) {
            for (Thread thread : weatherStationSimulatorList) {
                thread.interrupt();
            }
            e.printStackTrace();
        }



        if(isDebugActive()) {
            System.out.println("PROGRAM FINISHED");
        }
    }


    /**
     * Produce weather events from different locations, typically in the same city. Each standard weather event includes a location and a temperature.
     * Produce extreme weather event. Each extreme weather event includes a location and type of alert.
     */
    public void run () {
        if(isDebugActive()) {
            System.out.println("THREAD STARTED - Weather Station: " + this.weatherStation + " | Locations: " + Arrays.toString(this.locations));
        }

        Properties properties = new Properties();
        Random rand = new Random();

        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, this.weatherStation);

        properties.put("group.id", "producer-" + this.weatherStation);

        //Assign localhost id
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, WeatherStationSimulator.kafkaBrokerAddress);

        //Set acknowledgements for producer requests.
        properties.put("acks", "all");

        //If the request fails, the producer can automatically retry,
        properties.put("retries", 0);

        //Specify buffer size in config
        properties.put("batch.size", 16384);

        //Reduce the no of requests less than 0
        properties.put("linger.ms", 1);

        //The buffer.memory controls the total amount of memory available to the producer for buffering.
        properties.put("buffer.memory", 33554432);

        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass());

        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producerStandardWeatherTopic = new KafkaProducer<>(properties);
        Producer<String, String> producerWeatherAlertsTopic = new KafkaProducer<>(properties);

        while(true) {
            String location = this.locations[rand.nextInt(this.locations.length)];
            double temperature = rand.nextDouble((30.0 - 5.0) + 1.0) + 5.0;


            String json = "{\"location\": \"" + location + "\", \"temperature\": \"" + temperature + "\"}";

            if(isDebugActive()) {
                System.out.println("THREAD WEATHER STATION \"" + this.weatherStation + "\" - Location: " + location + " | Temperature: " + temperature + " | StandardWeatherTopic: " + json);
            }
            producerStandardWeatherTopic.send(new ProducerRecord<String, String>(WeatherStationSimulator.standardWeatherTopic, this.weatherStation, json));

            if(rand.nextInt(2) == 0) {
                String alert = WeatherStationSimulator.typeAlerts[rand.nextInt(WeatherStationSimulator.typeAlerts.length)];
                json = "{\"location\": \"" + location + "\", \"alert\": \"" + alert + "\"}";

                if (isDebugActive()) {
                    System.out.println("THREAD WEATHER STATION \"" + this.weatherStation + "\" - Location: " + location + " | Alert: " + alert + " | WeatherAlertsTopic: " + json);
                }
                producerWeatherAlertsTopic.send(new ProducerRecord<String, String>(WeatherStationSimulator.weatherAlertsTopic, this.weatherStation, json));
            }
            try {
                Thread.sleep(rand.nextLong((10000 - 1000) + 1) + 1000);
            } catch (InterruptedException e) {
                break;
            }
        }
        producerStandardWeatherTopic.close();
        producerWeatherAlertsTopic.close();

        if(isDebugActive()) {
            System.out.println("THREAD WEATHER STATION \"" + this.weatherStation + "\" - Finished");
        }

        System.exit(0);
    }

    public static boolean isDebugActive() {
        return debugActive;
    }

    public static void setDebugActive(boolean debugActive) {
        WeatherStationSimulator.debugActive = debugActive;
    }
}
