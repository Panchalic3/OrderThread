//package com.example.demo.config;
//
//import jakarta.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.bucketlist.db1.repository",
//        entityManagerFactoryRef = "db1EntityManagerFactory",
//        transactionManagerRef = "db1TransactionManager"
//)
//public class Db1Config {
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    public DataSource db1DataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            DataSource db1DataSource) {
//
//        return builder
//                .dataSource(db1DataSource)
//                .packages("com.bucketlist.db1.entity")
//                .persistenceUnit("db1")
//                .build();
//    }
//    @Bean
//    public PlatformTransactionManager db1TransactionManager(
//            EntityManagerFactory db1EntityManagerFactory) {
//
//        return new JpaTransactionManager(db1EntityManagerFactory);
//    }
//
//}
