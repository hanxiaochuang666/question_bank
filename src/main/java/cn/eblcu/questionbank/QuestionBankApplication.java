package cn.eblcu.questionbank;

import com.spring4all.mongodb.EnableMongoPlus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan  //使过滤器起作用
@EnableSwagger2
@EnableTransactionManagement
@EnableMongoPlus
@MapperScan("cn.eblcu.questionbank.persistence.dao")
@EnableScheduling
public class QuestionBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionBankApplication.class, args);
    }

}
