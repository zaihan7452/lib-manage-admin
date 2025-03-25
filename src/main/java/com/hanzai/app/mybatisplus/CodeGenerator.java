package com.hanzai.app.mybatisplus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

public class CodeGenerator {

    public static void main(String[] args) {
        // Use FastAutoGenerator for easy code generator setup
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/lib-manage-admin?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC",
                        "root", "123456")
                .globalConfig(builder -> {
                    builder.author("Han Zai") // Set author
                            .outputDir(System.getProperty("user.dir")) // Set output directory
                            .disableOpenDir(); // Disable opening the output directory after generation
                })
                .packageConfig(builder -> {
                    builder.parent("com.hanzai.app") // Set parent package name
                            .entity("entity") // Set entity class package name
                            .mapper("dao") // Set Mapper interface package name
                            .service("service") // Set Service interface package name
                            .serviceImpl("service.impl") // Set Service implementation class package name
                            .xml("mapper"); // Set Mapper XML files package name
                })
                .strategyConfig(builder -> {
                    builder.addInclude() // Set the table names to generate code for
                            .addExclude("flyway_schema_history") // Exclude the flyway_schema_history table
                            .entityBuilder()
                            .formatFileName("%sEntity") // Set the entity class name format
                            .enableChainModel() // Enable chain model
                            .addTableFills( // Set the fill fields
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE))
                            .enableLombok() // Enable Lombok
                            .logicDeleteColumnName("deleted") // Set the logical deletion column name
                            .enableTableFieldAnnotation() // Enable field annotations
                            .controllerBuilder().disable(); // Disable generating controller code
                })
                .templateEngine(new FreemarkerTemplateEngine()) // Use Freemarker template engine
                .execute(); // Execute the code generation
    }

}
