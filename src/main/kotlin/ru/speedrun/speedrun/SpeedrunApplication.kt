package ru.speedrun.speedrun

import org.springframework.boot.autoconfigure.SpringBootApplication
//import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.runApplication

@SpringBootApplication
//@ComponentScan(basePackages = ["ru.speedrun"])
class SpeedrunApplication

fun main(args: Array<String>) {
	runApplication<SpeedrunApplication>(*args)
}
