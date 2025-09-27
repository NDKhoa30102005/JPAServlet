package khoa.vn.Configs;

import jakarta.persistence.*;

public class JPA_Config {
	public static EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dataSource");
		return factory.createEntityManager();
	}
}
