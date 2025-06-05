package com.ang.rest;

import io.quarkus.runtime.annotations.QuarkusMain;
import io.quarkus.runtime.Quarkus;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@QuarkusMain
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1", description = "My API description"))
public class RestApplication {

    public static void main(String[] args) {
        Quarkus.run(args);
    }

}
