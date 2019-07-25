package senntyou.sbs.mbg;

import java.util.Arrays;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class CommentGenerator extends DefaultCommentGenerator {
  // whether add column comment in database to java class fields by @ApiModelProperty
  private boolean addRemarkComments = false;
  // fields to ignore when serialize object to json string
  private String[] ignoreFields;
  private static final String EXAMPLE_SUFFIX = "Example";
  private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME =
      "io.swagger.annotations.ApiModelProperty";
  private static final String JSON_IGNORE_FULL_CLASS_NAME =
      "com.fasterxml.jackson.annotation.JsonIgnore";

  @Override
  public void addConfigurationProperties(Properties properties) {
    super.addConfigurationProperties(properties);
    this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    String ignoreFieldsString = properties.getProperty("ignoreFields");
    if (ignoreFieldsString != null && ignoreFieldsString.length() > 0) {
      ignoreFields = ignoreFieldsString.split(",");
    }
  }

  @Override
  public void addFieldComment(
      Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    String remarks = introspectedColumn.getRemarks();

    if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
      addFieldJavaDoc(field, remarks);

      if (remarks.contains("\"")) {
        remarks = remarks.replace("\"", "'");
      }

      field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
    }

    if (ignoreFields != null && ignoreFields.length > 0) {
      if (Arrays.binarySearch(ignoreFields, field.getName()) > -1) {
        field.addJavaDocLine("@JsonIgnore");
      }
    }
  }

  private void addFieldJavaDoc(Field field, String remarks) {
    field.addJavaDocLine("/**");

    String[] remarkLines = remarks.split(System.getProperty("line.separator"));
    for (String remarkLine : remarkLines) {
      field.addJavaDocLine(" * " + remarkLine);
    }
    addJavadocTag(field, false);
    field.addJavaDocLine(" */");
  }

  @Override
  public void addJavaFileComment(CompilationUnit compilationUnit) {
    super.addJavaFileComment(compilationUnit);

    if (!compilationUnit.isJavaInterface()
        && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
      // ApiModelProperty
      compilationUnit.addImportedType(
          new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));

      // JsonIgnore
      compilationUnit.addImportedType(new FullyQualifiedJavaType(JSON_IGNORE_FULL_CLASS_NAME));
    }
  }
}
