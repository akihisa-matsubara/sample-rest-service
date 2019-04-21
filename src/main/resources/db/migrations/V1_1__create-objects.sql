
/* Drop Tables */

-- DROP TABLE M_POST_CODE;
-- DROP TABLE T_CUSTOMER_FAMILY_RELATION;
-- DROP TABLE T_CUSTOMER;
-- DROP TABLE T_FAMILY;



/* Drop Sequences */

-- DROP SEQUENCE SEQ_CUSTOMER_NO;




/* Create Sequences */

CREATE SEQUENCE ${schemaName}.SEQ_CUSTOMER_NO AS INTEGER INCREMENT BY 1 MINVALUE 1 MAXVALUE 999999999 START WITH 1 CYCLE NOCACHE;



/* Create Tables */

-- �X�֔ԍ��}�X�^
CREATE TABLE ${schemaName}.M_POST_CODE
(
	-- �X�֔ԍ�
	POST_CODE char(7) NOT NULL,
	-- �s���{����
	PREFECTURE_NAME varchar(60),
	-- �s�撬����
	CITY_NAME varchar(60),
	-- ���於
	TOWN_NAME varchar(60),
	-- �o�[�W����
	VERSION int DEFAULT 1 NOT NULL,
	-- �쐬���[�U�[ID
	CREATION_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �쐬����
	CREATION_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
	-- �X�V���[�U�[ID
	UPDATED_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �X�V����
	UPDATED_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL
);


-- �ڋq
CREATE TABLE ${schemaName}.T_CUSTOMER
(
	-- �ڋq�ԍ�
	CUSTOMER_NO char(8) NOT NULL,
	-- ��������
	NAME_KANJI varchar(60) NOT NULL,
	-- �����J�i
	NAME_KANA varchar(60) NOT NULL,
	-- ����
	GENDER char(1) NOT NULL,
	-- ���N����
	BIRTHDAY date NOT NULL,
	-- �X�֔ԍ�
	ADDRESS_ZIP varchar(7) NOT NULL,
	-- �Z��
	ADDRESS varchar(200) NOT NULL,
	-- �o�[�W����
	VERSION int DEFAULT 1 NOT NULL,
	-- �쐬���[�U�[ID
	CREATION_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �쐬����
	CREATION_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
	-- �X�V���[�U�[ID
	UPDATED_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �X�V����
	UPDATED_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
	PRIMARY KEY (CUSTOMER_NO)
);


-- �ڋq�Ƒ��֘A
CREATE TABLE ${schemaName}.T_CUSTOMER_FAMILY_RELATION
(
	-- �ڋq�ԍ�
	CUSTOMER_NO char(8) NOT NULL,
	-- �Ƒ��ԍ�
	FAMILY_NO char(8) NOT NULL,
	-- �o�[�W����
	VERSION int DEFAULT 1 NOT NULL,
	-- �쐬���[�U�[ID
	CREATION_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �쐬����
	CREATION_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
	-- �X�V���[�U�[ID
	UPDATED_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �X�V����
	UPDATED_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
	PRIMARY KEY (CUSTOMER_NO, FAMILY_NO)
);


-- �Ƒ�
CREATE TABLE ${schemaName}.T_FAMILY
(
	-- �Ƒ��ԍ�
	FAMILY_NO char(8) NOT NULL,
	-- ��������
	NAME_KANJI varchar(60) NOT NULL,
	-- �����J�i
	NAME_KANA varchar(60) NOT NULL,
	-- ����
	GENDER char(1) NOT NULL,
	-- ���N����
	BIRTHDAY date NOT NULL,
	-- �o�[�W����
	VERSION int DEFAULT 1 NOT NULL,
	-- �쐬���[�U�[ID
	CREATION_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �쐬����
	CREATION_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
	-- �X�V���[�U�[ID
	UPDATED_USER_ID varchar(20) DEFAULT '' NOT NULL,
	-- �X�V����
	UPDATED_DATE timestamp DEFAULT CURRENT TIMESTAMP NOT NULL,
	PRIMARY KEY (FAMILY_NO)
);



/* Create Foreign Keys */

ALTER TABLE ${schemaName}.T_CUSTOMER_FAMILY_RELATION
	ADD FOREIGN KEY (CUSTOMER_NO)
	REFERENCES T_CUSTOMER (CUSTOMER_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE ${schemaName}.T_CUSTOMER_FAMILY_RELATION
	ADD FOREIGN KEY (FAMILY_NO)
	REFERENCES T_FAMILY (FAMILY_NO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



