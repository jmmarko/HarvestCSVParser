package eu.jmlabs.research.harvestCSVParser;

import com.marko.research.jiraRESTparser.entity.JiraProjectEntity;
import com.marko.research.jiraRESTparser.util.ParseJiraData;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Author: Mark0
 * Created: 19/10/15
 * Updated: 19/10/15
 */
public class HarvestCSVParser {

    private static Logger log = LoggerFactory.getLogger(HarvestCSVParser.class.getName());

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConn");
        EntityManager em = emf.createEntityManager();

        em.close();
        emf.close();
    }
}
