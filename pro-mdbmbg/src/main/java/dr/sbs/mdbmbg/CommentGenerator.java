package dr.sbs.mdbmbg;

import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

public class CommentGenerator extends DefaultCommentGenerator {
  // whether add column comment in database to java class fields by @ApiModelProperty
  private boolean addRemarkComments = false;
  private static final String EXAMPLE_SUFFIX = "Example";
  private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME =
      "io.swagger.annotations.ApiModelProperty";
  private static final String JSON_IGNORE_FULL_CLASS_NAME =
      "com.fasterxml.jackson.annotation.JsonIgnore";
  private static final String JSON_FILTER_FULL_CLASS_NAME =
      "com.fasterxml.jackson.annotation.JsonFilter";

  // to generate position of @ApiModelProperty
  private String currentTable;
  private int fieldIndex = 1;

  @Override
  public void addConfigurationProperties(Properties properties) {
    super.addConfigurationProperties(properties);
    this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
  }

  @Override
  public void addFieldComment(
      Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    String remarks = introspectedColumn.getRemarks();

    String table = introspectedTable.getFullyQualifiedTable().toString();
    if (!table.equals(currentTable)) {
      currentTable = table;
      fieldIndex = 1;
    } else {
      fieldIndex += 1;
    }

    if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
      addFieldJavaDoc(field, remarks);

      if (remarks.contains("\"")) {
        remarks = remarks.replace("\"", "'");
      }

      field.addJavaDocLine(
          "@ApiModelProperty(value = \"" + remarks + "\", position = " + fieldIndex + ")");
    }

    List<String> commonIgnoreFields = CommentGeneratorProperty.commonIgnoreFields;
    if (commonIgnoreFields != null && commonIgnoreFields.size() > 0) {
      if (commonIgnoreFields.contains(field.getName())) {
        field.addJavaDocLine("@JsonIgnore");
        return;
      }
    }

    List<String> specIgnoreFields = CommentGeneratorProperty.specIgnoreFields.get(table);
    if (specIgnoreFields != null && specIgnoreFields.size() > 0) {
      if (specIgnoreFields.contains(field.getName())) {
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
      // JsonFilter
      compilationUnit.addImportedType(new FullyQualifiedJavaType(JSON_FILTER_FULL_CLASS_NAME));
    }
  }

  @Override
  public void addModelClassComment(
      TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
    super.addClassComment(topLevelClass, introspectedTable);

    String table = introspectedTable.getFullyQualifiedTable().toString();

    String commonFilterName = CommentGeneratorProperty.commonFilterName;
    if (commonFilterName != null && commonFilterName.length() > 0) {
      topLevelClass.addJavaDocLine("@JsonFilter(\"" + commonFilterName + "\")");
      return;
    }

    String specFilterName = CommentGeneratorProperty.specFilterName.get(table);
    if (specFilterName != null && specFilterName.length() > 0) {
      topLevelClass.addJavaDocLine("@JsonFilter(\"" + specFilterName + "\")");
    }
  }
}
