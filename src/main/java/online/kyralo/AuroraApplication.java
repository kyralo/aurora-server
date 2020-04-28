package online.kyralo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 王宸
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "online.kyralo.*.dao")
public class AuroraApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuroraApplication.class, args);
	}

}
