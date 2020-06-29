package br.com.mindbet.common.model

import org.junit.Test
import java.util.*

class MomentTest {

    @Test
    fun plus() {
        val result = Moment(7, 8, 2019) + 2
        assert(result.toString() == "09/08/2019")
    }

    @Test
    fun minus() {
        val result = Moment(7, 8, 2019) - 2
        assert(result.toString() == "05/08/2019")
    }

    @Test
    fun compareTo() {
        val equal = Moment(7, 8, 2019).compareTo(Moment(7, 8, 2019))
        assert(equal == 0)

        val before = Moment(6, 8, 2019).compareTo(Moment(7, 8, 2019))
        assert(before == -1)

        val after = Moment(8, 8, 2019).compareTo(Moment(7, 8, 2019))
        assert(after == 1)
    }

    @Test
    fun equals() {
        val equal = Moment(7, 8, 2019) == (Moment(7, 8, 2019))
        val equal2 = Moment() == Moment()
        assert(equal)
    }

    @Test
    fun toLong() {
        val time = 12345678L
        assert(Moment(time).toLong() == time)
    }

    @Test
    fun toDate() {
        val date = Date()
        assert(Moment(date).toDate() == date)
    }

    @Test
    fun parseDefaultFormat() {
        val date = Moment(7, 8, 2019)
        assert(date.toString() == "07/08/2019")
    }

    @Test
    fun parseCustomFormat() {
        val date = Moment(7, 8, 2019)
        assert(date.toString(Moment.DAYS, Moment.FULL_MONTH, Moment.SIMPLE_YEAR, " de ") == "07 de Agosto de 19")

        assert(date.toString("${Moment.DAYS}'|'${Moment.MONTH}'|'${Moment.FULL_YEAR}") == "07|08|2019")
    }
}