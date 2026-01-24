//package com.example.demo.config;
//
//import org.springframework.context.annotation.Configuration;
//
//import jakarta.persistence.EntityManagerFactory;
//import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.bucketlist.db2.repository",
//        entityManagerFactoryRef = "db2EntityManagerFactory",
//        transactionManagerRef = "db2TransactionManager"
//)
//public class Db2Config {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    public DataSource db2DataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            DataSource db2DataSource) {
//
//        return builder
//                .dataSource(db2DataSource)
//                .packages("com.bucketlist.db2.entity")
//                .persistenceUnit("db2")
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager db2TransactionManager(
//            EntityManagerFactory db2EntityManagerFactory) {
//
//        return new JpaTransactionManager(db2EntityManagerFactory);
//    }
//}
