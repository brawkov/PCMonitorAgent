import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import oshi.*
import oshi.SystemInfo
import oshi.hardware.*
import oshi.software.common.AbstractOSFileStore
import oshi.software.os.*

import oshi.util.Util.sleep
import java.lang.Math.round

class firstclass {

    @Serializable
    data class StateTask(
        var taskPid: Int,
        var taskName: String,
        var taskUser: String,
        var taskCpuLoad: Float? = 0F,
        var taskRam: Int,
        var taskTime: Long,
    )

    @Serializable
    data class StatePC(
        val pcId: Long? = 0,
        var pcName: String,
        var pcActive: Boolean = true,
        var pcCpuPhysCoresInf: Short? = 0,
        var pcCpuLogicCoresInf: Short? = 0,
        var pcRamInf: Float? = 0F,
        var pcRomInf: Float? = 0F,
        var pcCpuFrequency: Float? = 0F,
        var pcCpuLoad: Float? = 0F,
        var pcRamState: Float? = 0F,
        var pcRomState: Float? = 0F,
        var pcTimeWork: Long? = 0,
        var pcTask: List<StateTask>
    )

    fun firstfun(): StatePC {

//        val toGb = 1000000000
        val si = SystemInfo()
        val osFileStore: OperatingSystem

        val hal: HardwareAbstractionLayer = si.hardware
        val cpuLoadTicks = hal.processor.systemCpuLoadTicks

        val os = si.operatingSystem
        val timeWork = os.systemUptime

        val cpu: CentralProcessor = hal.processor

        sleep(300)
        val loadCPU: Float =
            round(hal.processor.getSystemCpuLoadBetweenTicks(cpuLoadTicks).toFloat() * 1000).toFloat() / 10

        val pcCpuPhysCoresInf: Short = cpu.physicalProcessorCount.toShort()
        val pcCpuLogicCoresInf: Short = cpu.logicalProcessorCount.toShort()

        val cpuFreqArray = hal.processor.currentFreq

        var cpuFreq: Float = 0F
        cpuFreqArray.forEach { cpuFreq += it.toFloat() / 1000000000 }
        cpuFreq = round(cpuFreq / cpuFreqArray.count() * 100).toFloat() / 100

        var sizeROM: Float = 0F
        var loadROM: Float = 0F
        os.fileSystem.getFileStores(true).forEach {
            sizeROM += it.freeSpace.toFloat() / 1000 / 1000 / 1000
            loadROM += it.totalSpace.toFloat() / 1000 / 1000 / 1000
        }
        sizeROM = round(sizeROM * 10).toFloat() / 10
        loadROM = round(loadROM * 10).toFloat() / 10

        val RAM: Float = round(si.hardware.memory.total.toFloat() / 1024 / 1024 / 1024 * 10).toFloat() / 10
        val loadRAM: Float =
            round((si.hardware.memory.total - si.hardware.memory.available).toFloat() / 1024 / 1024 / 1024 * 10).toFloat() / 10
        val hostName = os.networkParams.hostName
//        println("Host Name" + hostName)

//        println("Physical core: " + pcCpuPhysCoresInf)
//        println("Logical Core: " + pcCpuLogicCoresInf)
//        println("Cpu freq: " + cpuFreq)
//        println("LoadCPU: " + loadCPU)

//        println("load RAM: " + loadRAM)
//        println("RAM: " + RAM)

//        println("ROM: " + sizeROM)
//        println("Load ROM: " + loadROM)

//        println("time: " + timeWork)

        val listTask: MutableList<StateTask> = mutableListOf()


        val listProccesor = os.processes
        listProccesor.forEach {
//            println(
//                "listProccesor: " + it.processID + " " + it.name + " " + it.user + " " + it.residentSetSize / 1024 / 1024 + " " + round(
//                    it.getProcessCpuLoadBetweenTicks(it) * 1000
//                ).toFloat() / 10 + " " + it.upTime / 1000
//            )
            listTask += StateTask(
                it.processID,
                it.name,
                it.user,
                round(it.getProcessCpuLoadBetweenTicks(it) * 1000).toFloat() / 10,
                round(it.residentSetSize.toFloat() / 1024 / 1024),
                it.upTime / 1000,
            )
        }


        val statePC: StatePC = StatePC(
            9998,
            hostName,
            true,
            pcCpuPhysCoresInf,
            pcCpuLogicCoresInf,
            RAM,
            sizeROM,
            cpuFreq,
            loadCPU,
            loadRAM,
            loadROM,
            timeWork,
            listTask
        )
//        val string = Json.encodeToString(statePC)
//        println("string $string") // {"name":"kotlinx.serialization","language":"Kotlin"}
//        // Deserializing back into objects
//        val obj = Json.decodeFromString<TestObject>(string)
//        println("decode: $obj") // Project(name=kotlinx.serialization, language=Kotlin)


        return statePC
    }
}