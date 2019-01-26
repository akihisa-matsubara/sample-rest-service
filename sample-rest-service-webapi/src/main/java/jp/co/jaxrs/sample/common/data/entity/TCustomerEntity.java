package jp.co.jaxrs.sample.common.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "T_CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
		@NamedQuery(name = TCustomerEntity.FIND_ALL, query = "SELECT t FROM TCustomerEntity t ORDER BY t.customerNo"),
		@NamedQuery(name = TCustomerEntity.FIND_BY_ID, query = "SELECT t FROM TCustomerEntity t WHERE t.customerNo = :customerNo"),
		@NamedQuery(name = TCustomerEntity.UPDATE_BY_ID, query = "UPDATE TCustomerEntity t SET t.nameKanji = :nameKanji, t.nameKana = :nameKana, t.gender = :gender, t.birthday = :birthday, t.addressZip = :addressZip, t.address = :address WHERE t.customerNo = :customerNo"),
		@NamedQuery(name = TCustomerEntity.DELETE_BY_ID, query = "DELETE FROM TCustomerEntity t WHERE t.customerNo = :customerNo")
})
public class TCustomerEntity extends DbBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL = "TCustomer.findAll";
	public static final String FIND_BY_ID = "TCustomer.findById";
	public static final String UPDATE_BY_ID = "TCustomer.updateById";
	public static final String DELETE_BY_ID = "TCustomer.deleteById";

	@Id
	@Column(name = "CUSTOMER_NO")
	private String customerNo;

	@Column(name = "NAME_KANJI")
	private String nameKanji;

	@Column(name = "NAME_KANA")
	private String nameKana;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "BIRTHDAY")
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Column(name = "ADDRESS_ZIP")
	private String addressZip;

	@Column(name = "ADDRESS")
	private String address;

}
