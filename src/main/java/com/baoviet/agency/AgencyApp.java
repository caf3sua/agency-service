package com.baoviet.agency;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.baoviet.agency.config.ApplicationProperties;
import com.baoviet.agency.config.DefaultProfileUtil;
import com.baoviet.agency.config.GateWay123PayConfig;
import com.baoviet.agency.config.GateWayMomoConfig;
import com.baoviet.agency.config.GateWayViettelPayConfig;
import com.baoviet.agency.config.GateWayVnPayConfig;

import io.github.jhipster.config.JHipsterConstants;

@ComponentScan
@EnableAutoConfiguration(exclude = {MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class})
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class ,GateWay123PayConfig.class, GateWayMomoConfig.class, GateWayViettelPayConfig.class, GateWayVnPayConfig.class})
//@EnableCaching
public class AgencyApp {

    private static final Logger log = LoggerFactory.getLogger(AgencyApp.class);

    private final Environment env;

    public AgencyApp(Environment env) {
        this.env = env;
    }

    /**
     * Initializes KpiApi.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(AgencyApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        
        String hostAddress = "";
        try {
        	hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
        	hostAddress = "";
        }
        
        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}\n\t" +
                "External: \t{}://{}:{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            env.getProperty("server.port"),
            protocol,
            hostAddress,
            env.getProperty("server.port"),
            env.getActiveProfiles());
    }
    
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//      TomcatEmbeddedServletContainerFactory tomcat =
//          new TomcatEmbeddedServletContainerFactory() {
//
//            @Override
//            protected void postProcessContext(Context context) {
//              SecurityConstraint securityConstraint = new SecurityConstraint();
//              securityConstraint.setUserConstraint("CONFIDENTIAL");
//              SecurityCollection collection = new SecurityCollection();
//              collection.addPattern("/*");
//              securityConstraint.addCollection(collection);
//              context.addConstraint(securityConstraint);
//            }
//          };
//      tomcat.addAdditionalTomcatConnectors(createHttpConnector());
//      return tomcat;
//    }
//
//    @Value("${server.http.port}")
//    private int serverPortHttp;
//
//    @Value("${server.port}")
//    private int serverPortHttps;
//
//    private Connector createHttpConnector() {
//      Connector connector =
//          new Connector("org.apache.coyote.http11.Http11NioProtocol");
//      connector.setScheme("http");
//      connector.setSecure(false);
//      connector.setPort(serverPortHttp);
//      connector.setRedirectPort(serverPortHttps);
//      return connector;
//    }
}
