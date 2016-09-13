package rest.config;

/**
 * Created by jtduan on 2016/9/13.
 */
import org.springframework.boot.context.embedded.*;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class ServletContainerConfig implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
//        container.setPort(9000);
    }
}
