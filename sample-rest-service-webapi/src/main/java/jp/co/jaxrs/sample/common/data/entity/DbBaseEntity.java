package jp.co.jaxrs.sample.common.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import jp.co.jaxrs.sample.common.data.converter.LocalDateTimeToTimestampConverter;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class DbBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	@Column(name = "VERSION")
	private int version;

	@Column(name = "CREATION_USER_ID", updatable = false)
	private String creationUserId;

	@Column(name = "CREATION_DATE", updatable = false)
	@Convert(converter = LocalDateTimeToTimestampConverter.class)
	private LocalDateTime creationDate;

	@Column(name = "UPDATED_USER_ID")
	private String updatedUserId;

	@Column(name = "UPDATED_DATE")
	@Convert(converter = LocalDateTimeToTimestampConverter.class)
	private LocalDateTime updatedDate;
}
