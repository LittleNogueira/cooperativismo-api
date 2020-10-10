package br.com.nogueira.cooperativismo;

import br.com.nogueira.cooperativismo.v1.controllers.PautaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableSwagger2
public class CooperativismoApplication {

	private static Logger Logger = LoggerFactory.getLogger(CooperativismoApplication.class);

	public static void main(String[] args) {
		Logger.info("Iniciando API de Cooperativismo");
		SpringApplication.run(CooperativismoApplication.class, args);
		Logger.info("API de Cooperativismo inciada e pronta para receber requisições");
	}

}
