package ru.speedrun.liquibase

import liquibase.Scope
import liquibase.changelog.ChangeLogParameters
import liquibase.changelog.ChangeSet
import liquibase.changelog.DatabaseChangeLog
import liquibase.parser.core.yaml.YamlChangeLogParser
import liquibase.resource.ResourceAccessor

/**
 * Парсер YAML файлов миграций для Liquibase.
 *
 * Расширение стандартного YamlChangeLogParser.
 * Исправляет ошибку с некорректным названием файлов миграций при
 * использовании в распакованном jar-нике, убирая префикс `BOOT-INF/classes/`.
 *
 * WA для исправления ошибки https://github.com/liquibase/liquibase/issues/1755
 * по мотивам https://www.programmersought.com/article/61968339/
 *
 * @todo удалить WA когда (если) баг будет исправлен
 */
class YamlChangeLogParserWA : YamlChangeLogParser() {
    companion object {
        const val BOOT_CLASSES = "BOOT-INF/classes/"
    }

    init {
        val log = Scope.getCurrentScope().getLog(javaClass)
        log.warning("Overriding default YamlChangeLogParser for the glory of $BOOT_CLASSES")
    }

    override fun getPriority(): Int = super.getPriority() + 1

    override fun parse(
        physicalChangeLogLocation: String,
        changeLogParameters: ChangeLogParameters,
        resourceAccessor: ResourceAccessor
    ): DatabaseChangeLog {
        val changeLog = super.parse(physicalChangeLogLocation, changeLogParameters, resourceAccessor)
        for (set: ChangeSet in changeLog.changeSets) {
            val filePath = set.filePath
            if (filePath.startsWith(BOOT_CLASSES)) {
                set.filePath = filePath.substring(BOOT_CLASSES.length)
            }
        }
        return changeLog
    }
}
