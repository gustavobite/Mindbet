package br.com.mindbet.common.model

import org.junit.Test

class MoneyTest {

    @Test
    fun parse_with_BRL() {
        val money = Money(123.45345)
        assert(money.toString(3) == "R$ 123,453")
        assert(money.toString() == "R$ 123,45")
    }

    @Test
    fun parse_with_USD() {
        val money = Money(123.45345, Currency.USD)
        assert(money.toString(3) == "US$ 123,453")
        assert(money.toString() == "US$ 123,45")
    }

    @Test
    fun plus() {
        val result = Money(123.5) + Money(1.5)
        assert(result == Money(125.0))
    }

    @Test
    fun minus() {
        val result = Money(125.67) - Money(5.67)
        assert(result == Money(120.0000))
    }

    @Test
    fun plusAssign() {
        val money = Money(123.5)
        money += Money(12.55)
        assert(money == Money(136.05))
    }

    @Test
    fun minusAssign() {
        val money = Money(136.05)
        money -= Money(12.55)
        assert(money == Money(123.5))
    }

    @Test
    fun times() {
        val result = Money(136.05) * Money(1.1)
        assert(result == Money(149.655))
    }

    @Test
    fun div() {
        val result = Money(149.655) / Money(2.5)
        assert(result == Money(59.862))
    }

    @Test
    fun timesAssign() {
        val result = Money(136.05)
        result *= Money(1.1)
        assert(result == Money(149.655))
    }

    @Test
    fun divAssign() {
        val result = Money(149.655)
        result /= Money(2.5)
        assert(result == Money(59.862))
    }
}