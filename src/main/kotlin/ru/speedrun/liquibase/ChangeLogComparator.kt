package ru.redgift.liquibase

/**
 * Класс сравнения файлов миграции.
 *
 * Файлы миграции расположены в директориях именованные по версиям релиза. Liquibase сортирует по алфавиту полного пути,
 * и поэтому миграции `2.10` выполнятся раньше `2.2`. Данный класс исправляет это поведение и сортирует по конечному
 * имени файла.
 *
 */
class ChangeLogComparator : Comparator<String> {
    override fun compare(o1: String, o2: String): Int {
        val file1 = o1.substringAfterLast("/")
        val file2 = o2.substringAfterLast("/")
        return file1.compareTo(file2)
    }
}
