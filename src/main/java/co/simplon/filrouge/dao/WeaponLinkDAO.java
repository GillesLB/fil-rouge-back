package co.simplon.filrouge.dao;

/* interface !
*  Data Access Object : design pattern utilisé dans les architectures logicielles objet */
public interface WeaponLinkDAO {

	/* va supprimer le lien (et non les données)
	*  entre une arme et une affaire */
    public void deleteLinkWeapon(Long idCase, Long idWeapon) throws Exception;


}
