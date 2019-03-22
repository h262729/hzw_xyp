package com.hzw.xyp;

import com.hzw.xyp.base.jpa.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@SpringBootApplication
public class HzwXypAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(HzwXypAdminApplication.class, args);
    }

}
