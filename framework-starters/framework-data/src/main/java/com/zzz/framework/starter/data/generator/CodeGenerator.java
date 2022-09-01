package com.zzz.framework.starter.data.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.zzz.framework.starter.core.model.BaseModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author zhouzq
 * @date 2020/3/17
 * @desc 代码生成器
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeGenerator {
    public static void generatorCode(String author, String dbUrl, String username, String password) {
        FastAutoGenerator.create(new DataSourceConfig.Builder(dbUrl, username, password))
                .globalConfig((scanner, builder) -> builder.author(author)
                        .fileOverride()
                        .outputDir(scanner.apply("\n请输入项目路径:") + "/src/main/java")
                        .disableOpenDir())
                .packageConfig((scanner, builder) -> builder
                        .parent(scanner.apply("\n请输入项目包名:"))
                        .controller("api")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("mapper")
                        .entity("model.domain")
                        .xml("mappers"))
                .strategyConfig((scanner, builder) -> builder.addInclude(scanner.apply("\n请输入表名多个英文逗号分隔:").split(","))
                        .entityBuilder()
                        .superClass(BaseModel.class)
                        .enableLombok()
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .mapperBuilder()
                        .enableBaseColumnList().enableBaseResultMap().controllerBuilder().enableRestStyle())
                .execute();
    }
}
