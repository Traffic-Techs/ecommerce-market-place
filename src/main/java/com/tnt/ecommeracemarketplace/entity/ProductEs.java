package com.tnt.ecommeracemarketplace.entity;

import java.util.Date;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "product")
@Setting(settingPath = "static/es-settings.json")
@Getter
@Setter
public class ProductEs {

  @Id
  @Field(type = FieldType.Keyword)
  private String id;

  @Field(type = FieldType.Text)
  private String title;

  @Field(type = FieldType.Text)
  private String images;

  @Field(type = FieldType.Text)
  private String description;

  @Field(type = FieldType.Long)
  private Long cost;

  @Field(type = FieldType.Long)
  private Long amount;

  @Field(type = FieldType.Date)
  private Date register_date;

  @Field(type = FieldType.Boolean)
  private Boolean sale;
}
