package jp.co.jaxrs.sample.common.data.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import jp.co.jaxrs.sample.common.data.entity.TCustomerEntity;

@ApplicationScoped
public class TCustomerDao extends GenericDao<TCustomerEntity> {
	@SuppressWarnings("unchecked")
	public List<TCustomerEntity> findAll() {
		return (List<TCustomerEntity>) entityManager.createNamedQuery(TCustomerEntity.FIND_ALL).getResultList();
	}

	public TCustomerEntity findById(String customerNo) {
		return (TCustomerEntity) entityManager.createNamedQuery(TCustomerEntity.FIND_BY_ID)
				.setParameter("customerNo", customerNo)
				.getSingleResult();
	}

	public int updateById(TCustomerEntity entity) {
		return entityManager.createNamedQuery(TCustomerEntity.UPDATE_BY_ID)
				.setParameter("nameKanji", entity.getNameKanji())
				.setParameter("nameKana", entity.getNameKana())
				.setParameter("gender", entity.getGender())
				.setParameter("birthday", entity.getBirthday())
				.setParameter("addressZip", entity.getAddressZip())
				.setParameter("address", entity.getAddress())
				.setParameter("customerNo", entity.getCustomerNo())
				.executeUpdate();
	}

	public void deleteById(String customerNo) {
		entityManager.createNamedQuery(TCustomerEntity.DELETE_BY_ID)
				.setParameter("customerNo", customerNo)
				.executeUpdate();
	}
}
