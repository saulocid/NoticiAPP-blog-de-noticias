// package com.EGG.noticia.configurations;

// import javax.sql.DataSource;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
// import jakarta.persistence.EntityManagerFactory;

// @Configuration
// @EnableJpaRepositories(basePackages = "com.EGG.noticia.repositories")
// public class JpaConfig {

//     @Autowired
//     private DataSource dataSource;

//     @Bean
//     public EntityManagerFactory entityManagerFactory() {
//         LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//         factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//         factoryBean.setPackagesToScan("com.EGG.noticia.entities");
//         factoryBean.setDataSource(dataSource);
//         return factoryBean.getObject();
//     }

// }
