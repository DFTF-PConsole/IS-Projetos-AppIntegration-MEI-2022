package uc.mei.is.admin.command;

import org.springframework.web.reactive.function.client.WebClient;

import uc.mei.is.admin.ConsumerService;
import uc.mei.is.admin.entity.TempReadingsStandardWeatherEventsStation;

public class RetrieveTempReadingsStandardWeatherEventsStation extends Command {

    public RetrieveTempReadingsStandardWeatherEventsStation(String host, String basePath, String command) {
        super(host, basePath, command);
    }

    @Override
    public boolean execute(String... args) {
        System.out.println("Working...");
        ConsumerService cs = new ConsumerService(WebClient.create(""));
        cs.buildFluxGet(TempReadingsStandardWeatherEventsStation.class, getFullPath(), objs -> {
            
            TempReadingsStandardWeatherEventsStation ws = (TempReadingsStandardWeatherEventsStation) objs[0];
            System.out.println(ws.toString());
            
        });
        return true;
    }

    @Override
    public String dump(String... args) {
        System.out.println("Working...");
        ConsumerService cs = new ConsumerService(WebClient.create(""));
        return cs.buildFluxGet(getFullPath());
    }

    @Override
    public String console() {
        return "WSt # Temperature readings";// + getFullPath();
    }




    
}
