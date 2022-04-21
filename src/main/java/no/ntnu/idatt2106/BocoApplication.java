package no.ntnu.idatt2106;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import no.ntnu.idatt2106.model.DAO.GroupDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DAO.UserGroupDAO;
import no.ntnu.idatt2106.repository.GroupRepository;
import no.ntnu.idatt2106.repository.UserGroupRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "BoCo - API"),
		security =  @SecurityRequirement(name = "Account Token")
)
@SecurityScheme(name = "Account Token", scheme = "Bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class BocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BocoApplication.class, args);
	}

}
