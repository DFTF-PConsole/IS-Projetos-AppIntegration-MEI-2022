package uc.mei.is.server;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import io.r2dbc.spi.ConnectionFactory;
import uc.mei.is.server.generator.Generator;
import uc.mei.is.server.network.EmulateNetworkDelays;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		EmulateNetworkDelays.setActive(false);
		EmulateNetworkDelays.setSeed(0);
		EmulateNetworkDelays.setMinDelayMillis(0);
		EmulateNetworkDelays.setMaxDelayMillis(50);

		SpringApplication.run(ServerApplication.class, args);

		// Basta 1 vez para gerar dados aleatórios
		//generate();
	}

	public static void generate(){
		Generator g = new Generator(1111, 111,7, 9458651);
		Thread t1 = new Thread(g::runStudents);
		Thread t2 = new Thread(g::runProfessors);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
			g.runRelationships();
		} catch (InterruptedException e) {
			t1.interrupt();
			t2.interrupt();
			e.printStackTrace();
		}
	}

	@Bean
	ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		ResourceDatabasePopulator resource = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
		initializer.setDatabasePopulator(resource);
		return initializer;
	  }
}
