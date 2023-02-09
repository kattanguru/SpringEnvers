package com.nisum.SpringEnvers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class SpringEnversApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SpringEnversApplication.class);

	@Autowired
	private BookRepository repository;

	@Autowired
	TransactionTemplate transactionTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringEnversApplication.class, args);
	}

	@Override
	public void run(String... args) {

		log.info("StartApplication...");
		Book java = new Book();
		java.setName("Java");
		Book java1 = repository.save(java);


		java1.setName("Java-Java");
		Book finalJava = java1;
		java1 = repository.save(finalJava);

		System.out.println("\nfindAll()");
		repository.findAll().forEach(x -> System.out.println(x));

		java1.setName("Java-Java-Spring");
		Book finalJava1 = java1;
		java1 = repository.save(finalJava1);

		System.out.println("\nfindAll()");
		repository.findAll().forEach(x -> System.out.println(x));

		System.out.println("\nfindById(1L)");
		repository.findById(1l).ifPresent(x -> System.out.println(x));

		System.out.println("\nfindByName('Node')");
		repository.findByName("Node").forEach(x -> System.out.println(x));

		Revisions<Long, Book> revisions = repository.findRevisions(java1.getId());
		System.out.println("\nLatest Revision: "+ revisions.getLatestRevision());
		revisions.forEach(longBookRevision -> {
			System.out.println("\n Revision :"+ longBookRevision.getEntity().getName());
			System.out.println("\n Revision Metadata :"+ longBookRevision.getMetadata());
			System.out.println("\n Revision Number:"+ longBookRevision.getRequiredRevisionNumber());
			System.out.println("\n Revision Instant:"+ longBookRevision.getRequiredRevisionInstant());
		});

	}

}
