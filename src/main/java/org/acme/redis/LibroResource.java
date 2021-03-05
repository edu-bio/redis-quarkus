package org.acme.redis;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import java.util.List;

import io.smallrye.mutiny.Uni;

@Path("/libros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibroResource {

    @Inject
    LibroService service;

    @GET
    public Uni<List<String>> keys() {
        return service.keys();
    }

    @POST
    public Libro create(Libro libro) {
        service.set(libro.titulo, libro.paginas);
        return libro;
    }

    @GET
    @Path("/{titulo}")
    public Libro get(@PathParam("titulo") String titulo) {
        return new Libro(titulo, Integer.valueOf(service.get(titulo)));
    }

    @PUT
    @Path("/{titulo}")
    public void increment(@PathParam("titulo") String titulo, Integer paginas) {
        service.increment(titulo, paginas);
    }

    @DELETE
    @Path("/{titulo}")
    public Uni<Void> delete(@PathParam("titulo") String titulo) {
        return service.del(titulo);
    }
}