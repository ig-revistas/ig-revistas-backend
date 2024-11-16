package ar.edu.unq.gurpo2.revistas.config;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

/**
 * Read property from docker secret file.
 */
public class DockerSecretsProcessor implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		String springPassword = environment.getProperty("SPRING_DB_PASSWORD");
		if (springPassword!=null && springPassword.startsWith("/")) {
			Resource resource = new FileSystemResource(springPassword);
			if (resource.exists()) {
				try {
					String dbPassword = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
					System.setProperty("SPRING_DB_PASSWORD", dbPassword);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
