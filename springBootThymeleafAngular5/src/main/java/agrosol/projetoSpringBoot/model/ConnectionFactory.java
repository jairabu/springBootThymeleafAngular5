package agrosol.projetoSpringBoot.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	private static EntityManagerFactory emf =
			Persistence.createEntityManagerFactory("filmes");
	
	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
