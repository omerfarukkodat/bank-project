package com.kodat.of.bankproject;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Bank App",
                description = "Backend Rest API for Bank App",
                version = "v1.0",
                contact = @Contact(
                        name = "Faruk Kodat",
                        email = "farukkodat@gmail.com",
                        url = "https://github.com/omerfarukkodat/bank-project"
                ),
                license = @License(
                        name = "***",
                        url = "https://github.com/omerfarukkodat/bank-project"
                )

        ),
        externalDocs = @ExternalDocumentation(
                description = "Bank app documentation",
                url = "https://github.com/omerfarukkodat/bank-project"
        ))
public class BankProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankProjectApplication.class, args);
    }

}
