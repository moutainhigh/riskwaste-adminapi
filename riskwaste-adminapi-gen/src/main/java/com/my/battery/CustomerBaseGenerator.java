package com.my.battery;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * 生成指定数据表相关基础,主要生成 mo mapper xml
 * 
 * @author You
 * @date 2020年4月25日
 */
@SpringBootApplication
public class CustomerBaseGenerator implements ApplicationRunner {

    // mysql数据库连接配置
    @Value(value = "${spring.datasource.driverClassName}")
    private String dbDriverName;
    @Value(value = "${spring.datasource.url}")
    private String dbUrl;
    @Value(value = "${spring.datasource.username}")
    private String dbUsername;
    @Value(value = "${spring.datasource.password}")
    private String dbPassword;

    // 模块名称（用于包名生成：基础包名+模块名称，api项目\业务项目寻址）
    private final String moduleName = "battery";
    // 基础包名称
    private final String parentPackageName = "com.my";

    // 是否覆盖文件（默认：否）
    private final Boolean fileOverride = false;

    // 开发者
    private final String author = "weibocy";

    // api项目路径
    private String apiProjectPath = "";
    // biz项目路径
    private String bizProjectPath = "";

    // 数据源
    private DataSourceConfig dsc = new DataSourceConfig();

    // 要生成的表名称
    private String[] tables;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        config();
        apiGen();
        bizGen();
        System.exit(0);
    }

    /**
     * 生成器配置
     */
    private void config() {
//        scanner();

        // 路径设置
        final String userDir = System.getProperty("user.dir");
        apiProjectPath = userDir + "/../riskwaste-adminapi-api/src/main/java";
        bizProjectPath = userDir + "/../riskwaste-adminapi-biz/src/main/java";

        // 数据源配置
        dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(dbDriverName);
        dsc.setUsername(dbUsername);
        dsc.setPassword(dbPassword);
    }

    /**
     * api项目生成
     */
    private void apiGen() {
        // 代码生成器
        final AutoGenerator mpg = new AutoGenerator();

        // 数据源
        mpg.setDataSource(dsc);

        // 全局配置
        final GlobalConfig gc = new GlobalConfig();
        gc.setAuthor(author); // 作者
        gc.setOpen(false); // 是否打开输出目录
        gc.setActiveRecord(false);// 不需要ActiveRecord
        gc.setSwagger2(false); // 实体属性 Swagger2 注解
        gc.setFileOverride(fileOverride); // 是否文件覆盖
        gc.setOutputDir(apiProjectPath);  // api路径
        gc.setEntityName("%sMo"); // 指定生成实体,实体以Mo结尾命名
        mpg.setGlobalConfig(gc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackageName);
        pc.setModuleName(moduleName);
        pc.setEntity("mo");
        mpg.setPackageInfo(pc);

        // 配置模板
        final TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("/templates/mo.java");
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setXml(null);
        templateConfig.setMapper(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        final StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 表名生成策略(下划线转驼峰)
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 字段生成策略(下划线转驼峰)
        // 公共父类
        // strategy.setSuperEntityClass(packageName + ".common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        // strategy.setSuperControllerClass(packageName + ".common.BaseController");
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id");
        strategy.setInclude(tables);
        strategy.setRestControllerStyle(true); // 生成 @RestController 控制器
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setVersionFieldName("version"); // 乐观锁字段名称
//              strategy.setTablePrefix(pc.getModuleName() + "_"); // 表前缀
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * biz项目生成
     */
    private void bizGen() {
        // 代码生成器
        final AutoGenerator mpg = new AutoGenerator();

        // 数据源
        mpg.setDataSource(dsc);

        // 全局配置
        final GlobalConfig gc = new GlobalConfig();
        gc.setAuthor(author); // 作者
        gc.setOpen(false); // 是否打开输出目录
        gc.setActiveRecord(false);// 不需要ActiveRecord
        gc.setSwagger2(false); // 实体属性 Swagger2 注解
        gc.setFileOverride(fileOverride); // 是否文件覆盖
        gc.setOutputDir(bizProjectPath);  // biz路径
        gc.setXmlName("%sMapper"); // 指定生成mapperXML
        gc.setMapperName("%sMapper"); // 指定生成mapper
        gc.setEntityName("%sMo"); // 指定生成实体,实体以Mo结尾命名
        mpg.setGlobalConfig(gc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setParent(parentPackageName);
        pc.setModuleName(moduleName);
        pc.setEntity("mo");
        pc.setXml("mapper");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 配置模板
        final TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setXml("/templates/mapper.xml");
        templateConfig.setMapper("/templates/mapper.java");
        templateConfig.setEntity(null);
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        final StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); // 表名生成策略(下划线转驼峰)
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 字段生成策略(下划线转驼峰)
        // 公共父类
        // strategy.setSuperEntityClass(packageName + ".common.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        // strategy.setSuperControllerClass(packageName + ".common.BaseController");
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id");
//        strategy.setInclude(tables);
        strategy.setRestControllerStyle(true); // 生成 @RestController 控制器
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setVersionFieldName("version"); // 乐观锁字段名称
//              strategy.setTablePrefix(pc.getModuleName() + "_"); // 表前缀
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private void scanner() {
        final Scanner       scanner = new Scanner(System.in);
        final StringBuilder help    = new StringBuilder();
        help.append("请输入要生成表名，多个英文逗号[,]拼接：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            tables = scanner.next().split(",");
        }
        scanner.close();
    }

    // 程序入口
    public static void main(final String[] args) {
        SpringApplication.run(CustomerBaseGenerator.class, args);
    }
}
