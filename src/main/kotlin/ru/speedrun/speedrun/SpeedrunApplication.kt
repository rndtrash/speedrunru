package ru.speedrun.speedrun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpeedrunApplication

fun main(args: Array<String>) {
	runApplication<SpeedrunApplication>(*args)
}
