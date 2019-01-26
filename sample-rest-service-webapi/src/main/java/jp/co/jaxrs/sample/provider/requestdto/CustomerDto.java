package jp.co.jaxrs.sample.provider.requestdto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
	private String customerNo;

	private String nameKanji;

	private String nameKana;

	private String gender;

	private Date birthday;

	private String addressZip;

	private String address;
}
