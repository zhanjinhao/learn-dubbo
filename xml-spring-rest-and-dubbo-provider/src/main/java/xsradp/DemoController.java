package xsradp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @Author ISJINHAO
 * @Date 2022/3/26 17:27
 */
@Path("rest")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8", MediaType.TEXT_XML + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8"})
public interface DemoController {

    @GET
    @Path("test")
    HelloResult test(@QueryParam("msg") String msg);

    @GET
    @Path("test2")
    HelloResult test2(@QueryParam("msg") Long msg);

}
