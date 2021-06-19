package util

import edu.wpi.first.wpilibj2.command.CommandBase

abstract class AnalogInputCommand : CommandBase() {

    internal lateinit var controller: Controller
    private var firstRun: Boolean = true

    override fun execute() = analogExecute(
        this.controller.getControllerValues()
    )

    abstract fun analogExecute(controllerValues: ControllerValues?)
}
