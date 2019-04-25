package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.SWFetcher;

/**
 * REST Web Service
 *
 * @author Fen
 */
@Path("generic")
public class GenericResource {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("get/{id}")
    @RolesAllowed("user")
    public Response getOne(@PathParam("id") int id) {
            SWFetcher fetcher = new SWFetcher();
        try {
            String res = fetcher.getSwappiData(id);
            String result = (gson.toJson(res));
            return Response.ok().entity(result).build();
        } catch (IOException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getFive")
    @RolesAllowed("user")
    public Response getFive() {
        try {
            SWFetcher fetcher = new SWFetcher();
            List<String> res = fetcher.getSeveralSwappiData();
            String result = (gson.toJson(res));
            return Response.ok().entity(result).build();
        } catch (Exception ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.serverError().build();
        }
    }
}
