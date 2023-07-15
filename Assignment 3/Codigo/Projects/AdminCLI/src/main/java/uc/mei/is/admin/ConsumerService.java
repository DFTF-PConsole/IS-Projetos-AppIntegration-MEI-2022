package uc.mei.is.admin;

import org.springframework.web.reactive.function.client.WebClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@AllArgsConstructor
@Data
public class ConsumerService {
    private final WebClient webClient;

    public <T> void buildFluxGet(Class<T> clazz, String path, Consumer tc){
        webClient.get()
            .uri(path)
            .retrieve()
            .bodyToFlux(clazz)
            .doOnNext(tc::consume)
            .blockLast();
    }

    public String buildFluxGet(String path){
        return webClient.get()
            .uri(path)
            .retrieve()
            .bodyToFlux(String.class)
            .blockLast();

    }
}
