package co.simplon.filrouge.service;

import co.simplon.filrouge.dao.VehiculeLinkDAO;
import co.simplon.filrouge.dao.WeaponLinkDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//permet de déclarer un bean de service, c'est à dire de la couche métier
@Service
public class WeaponLinkService {

    @Autowired
    private WeaponLinkDAO dao;

    public void deleteLinkWeapon(Long idCase, Long idWeapon) throws Exception {
        dao.deleteLinkWeapon(idCase,idWeapon );
    }
}
