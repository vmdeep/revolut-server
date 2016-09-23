import io.netty.channel.Channel;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Application extends ResourceConfig {

    public Application() {
        packages("controllers;services");
    }

    public static void main(String[] args) {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        ResourceConfig resourceConfig = new ResourceConfig(new Application());
        Channel server = NettyHttpContainerProvider.createServer(baseUri, resourceConfig, false);
    }
}
