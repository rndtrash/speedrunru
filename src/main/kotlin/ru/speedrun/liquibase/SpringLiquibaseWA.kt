package ru.speedrun.liquibase

import liquibase.integration.spring.SpringLiquibase

/**
 * Класс-надстройка для кастомизации интеграции Spring-Liquibase
 *
 * Дополнительный конфигурационный класс, необходимый для подключения собственного YAML парсера.
 *
 * WA для исправления ошибки https://github.com/liquibase/liquibase/issues/1755
 *
 */
class SpringLiquibaseWA : SpringLiquibase() {
    init {
        liquibase.parser.ChangeLogParserFactory.getInstance().register(YamlChangeLogParserWA())
    }
}
