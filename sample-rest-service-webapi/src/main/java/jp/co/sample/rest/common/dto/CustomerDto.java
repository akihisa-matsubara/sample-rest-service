package jp.co.sample.rest.common.dto;

import java.util.Date;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 顧客Dto.
 */
@Getter
@Setter
public class CustomerDto {
  /**
   * WAS9でサポートしているPOJOに利用できるAnnotation一覧
   * (https://www.ibm.com/support/knowledgecenter/ja/SSAW57_9.0.0/com.ibm.websphere.nd.multiplatform.doc/ae/twbs_jaxrs_jsoncontent_pojo.html)
   *
   * org.codehaus.jackson.annotate.JsonAnySetter
   * org.codehaus.jackson.annotate.JsonAutoDetect
   * org.codehaus.jackson.annotate.JsonClass
   * org.codehaus.jackson.annotate.JsonContentClass
   * org.codehaus.jackson.annotate.JsonCreator
   * org.codehaus.jackson.annotate.JsonGetter
   * org.codehaus.jackson.annotate.JsonIgnore
   * org.codehaus.jackson.annotate.JsonIgnoreProperties
   * org.codehaus.jackson.annotate.JsonKeyClass
   * org.codehaus.jackson.annotate.JsonProperty
   * org.codehaus.jackson.annotate.JsonPropertyOrder
   * org.codehaus.jackson.annotate.JsonSetter
   * org.codehaus.jackson.annotate.JsonSubTypes
   * org.codehaus.jackson.annotate.JsonSubTypes.Type
   * org.codehaus.jackson.annotate.JsonTypeInfo
   * org.codehaus.jackson.annotate.JsonTypeName
   * org.codehaus.jackson.annotate.JsonValue
   * org.codehaus.jackson.annotate.JsonWriteNullProperties
   * org.codehaus.jackson.map.annotate.JsonCachable
   * org.codehaus.jackson.map.annotate.JsonDeserialize
   * org.codehaus.jackson.map.annotate.JsonSerialize
   * org.codehaus.jackson.map.annotate.JsonTypeIdResolver
   * org.codehaus.jackson.map.annotate.JsonTypeResolver
   * org.codehaus.jackson.map.annotate.JsonView
   */

  /** 顧客番号. */
  @Size(min = 8, max = 8)
  private String customerNo;

  /** 氏名漢字. */
  private String nameKanji;

  /** 氏名カナ. */
  private String nameKana;

  /** 性別. */
  private String gender;

  /** 生年月日. */
  private Date birthday;

  /** 郵便番号. */
  // @JsonProperty("addressZip") // XXX 名称の変更も可能？？
  private String addressZip;

  /** 住所. */
  private String address;

}
