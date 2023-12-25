package QAEngineer.ShareMySight;

import QAEngineer.ShareMySight.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class ShareMySightApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareMySightApplication.class, args);
	}

}
