package com.vdobrikov;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Set;

@Path("/validate")
public class AuthValidationResource {
    private final Set<String> tokens = Set.of(
                "open-sesame");

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response validate(@Length(min=1) String token) {
        return tokens.contains(token.strip()) ? Response.ok().build() : Response.status(Response.Status.UNAUTHORIZED).build();
    }
}