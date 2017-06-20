import ratpack.exec.Blocking;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.groovy.template.TextTemplateModule;
import ratpack.guice.Guice;

import static ratpack.groovy.Groovy.groovyTemplate;

import java.util.*;

public class Main {
  public static void main(String... args) throws Exception {
    RatpackServer.start(s -> s
        .serverConfig(c -> c
          .baseDir(BaseDir.find())
          .env())

        .registry(Guice.registry(b -> b
          .module(TextTemplateModule.class, conf -> conf.setStaticallyCompile(true))))

        .handlers(chain -> chain
            .get(ctx -> ctx.render(groovyTemplate("index.html")))
        )
    );
  }
}
