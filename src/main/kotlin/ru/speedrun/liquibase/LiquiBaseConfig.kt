package ru.speedrun.liquibase

import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

/**
 * Конфигурационный бин для кастомизации интеграции Spring-Liquibase
 *
 * Используется для вызова реализации SpringLiquibaseWA().
 *
 * @property dataSource Подключение к БД
 * @property properties Параметры Spring.Liquibase
 *
 */
@Configuration
@EnableConfigurationProperties(LiquibaseProperties::class)
data class LiquiBaseConfig(val dataSource: DataSource, val properties: LiquibaseProperties) {
    @Bean
    fun liquibase(): SpringLiquibase = SpringLiquibaseWA().also {
        it.dataSource = dataSource
        it.changeLog = properties.changeLog
        it.contexts = properties.contexts?.joinToString()
        it.defaultSchema = properties.defaultSchema
        it.liquibaseSchema = properties.liquibaseSchema
        it.liquibaseTablespace = properties.liquibaseTablespace
        it.isDropFirst = properties.isDropFirst
        it.labelFilter = properties.labelFilter?.joinToString()
        it.setShouldRun(properties.isEnabled)
        it.setChangeLogParameters(properties.parameters)
        it.setRollbackFile(properties.rollbackFile)
        it.isTestRollbackOnUpdate = properties.isTestRollbackOnUpdate
        it.isClearCheckSums = properties.isClearChecksums
    }
}
