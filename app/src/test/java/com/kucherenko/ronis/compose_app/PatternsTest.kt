package com.kucherenko.ronis.compose_app

import com.kucherenko.ronis.compose_app.patterns.bridge.BasicRemote
import com.kucherenko.ronis.compose_app.patterns.composite.Computer
import com.kucherenko.ronis.compose_app.patterns.composite.HardDrive
import com.kucherenko.ronis.compose_app.patterns.composite.Memory
import com.kucherenko.ronis.compose_app.patterns.composite.Processor
import com.kucherenko.ronis.compose_app.patterns.composite.RAM
import com.kucherenko.ronis.compose_app.patterns.composite.ROM
import com.kucherenko.ronis.compose_app.patterns.facade.UserRepository
import org.junit.Assert
import org.junit.Test

class PatternsTest {
    @Test
    fun testFacade() {
        val userRepository = UserRepository()
        val user = User("devid")

        userRepository.save(user)
        val retrievedUser = userRepository.findFirst()

        Assert.assertEquals(retrievedUser.login, "devid")
    }

    @Test
    fun testBridge() {
        val tv = TV()
        val phone = Phone()

        val tvRemote = BasicRemote(tv)
        val phoneRemote = BasicRemote(phone)

        tvRemote.volumeUp()
        tvRemote.volumeUp()
        tvRemote.volumeDown()

        phoneRemote.volumeUp()
        phoneRemote.volumeUp()
        phoneRemote.volumeUp()
        phoneRemote.volumeDown()

        Assert.assertEquals(tv.volume, 1)
        Assert.assertEquals(phone.volume, 2)
    }

    @Test
    fun testComposite(){
        val memory = Memory()
            .add(RAM())
            .add(ROM())
        val computer = Computer()
            .add(memory)
            .add(HardDrive())
            .add(Processor())

        Assert.assertEquals(computer.name, "PC")
        Assert.assertEquals(computer.price, 1425)
    }
}