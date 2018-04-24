package co.simplon.filrouge.repository;

import co.simplon.filrouge.dao.WeaponLinkDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/* c'est un marqueur pour n'importe quelle classe qui accomplit le rôle ou le stéréotype 
*  (aussi connu comme l'Objet d'Accès de Données ou DAO) d'un dépôt */
@Repository
public class JdbcWeaponDAO implements WeaponLinkDAO {

    final Logger log = LoggerFactory.getLogger(this.getClass());
    private DataSource datasource;

        @Autowired
        public JdbcWeaponDAO(JdbcTemplate jdbcTemplate) {
            this.datasource = jdbcTemplate.getDataSource();
        }

    @Override // crée une requête pour supprimer le lien
    public void deleteLinkWeapon(Long idCase, Long idWeapon) throws Exception {

        String sql = "DELETE FROM police_case_weapon WHERE police_case_id = ? AND weapon_id = ?";

        try (Connection connection = this.datasource.getConnection()) {
        	/* supprime la table de jointure (ici, arme / affaire)
        	*  correspondant aux id entrés */
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

                try {
                    pstmt.setLong(1, idCase);
                    pstmt.setLong(2, idWeapon);

                    logSQL(pstmt); // Log info
                    
                    int result = pstmt.executeUpdate(); // Run the the update query
                    if (result != 1) {
                        throw new Exception("id not found !");
                    }
                } catch (SQLException e) {
                    LoggerFactory.getLogger("SQL Error !:" + pstmt.toString() + e);
                    throw e;
                }
            }
        }
    }

    private void logSQL(PreparedStatement pstmt) {
        String sql;

        if (pstmt == null)
            return;

        sql = pstmt.toString().substring(pstmt.toString().indexOf(':') + 2);
        log.debug(sql);
    }
}
