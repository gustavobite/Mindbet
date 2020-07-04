package br.com.news

import br.com.news.model.News
import kotlinx.coroutines.delay

class NewsServiceMock: NewsService{
    override suspend fun getNews(): List<News> {
        delay(2000)
        return listOf(
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString(),
                description = generateDescription()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString(),
                description = generateDescription()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString(),
                description = generateDescription()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString(),
                description = generateDescription()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString(),
                description = generateDescription()
            )
        )
    }

    private fun generateDescription():String = "Mussum Ipsum, cacilds vidis litro abertis. Todo mundo vê os porris que eu tomo, mas ninguém vê os tombis que eu levo! Si num tem leite então bota uma pinga aí cumpadi! Em pé sem cair, deitado sem dormir, sentado sem cochilar e fazendo pose. In elementis mé pra quem é amistosis quis leo.\n" +
            "\n" +
            "Copo furadis é disculpa de bebadis, arcu quam euismod magna. Suco de cevadiss, é um leite divinis, qui tem lupuliz, matis, aguis e fermentis. Mé faiz elementum girarzis, nisi eros vermeio. Quem num gosta di mim que vai caçá sua turmis!\n" +
            "\n" +
            "Praesent malesuada urna nisi, quis volutpat erat hendrerit non. Nam vulputate dapibus. Posuere libero varius. Nullam a nisl ut ante blandit hendrerit. Aenean sit amet nisi. Viva Forevis aptent taciti sociosqu ad litora torquent. Delegadis gente finis, bibendum egestas augue arcu ut est.\n" +
            "\n" +
            "Vehicula non. Ut sed ex eros. Vivamus sit amet nibh non tellus tristique interdum. Nec orci ornare consequat. Praesent lacinia ultrices consectetur. Sed non ipsum felis. Mais vale um bebadis conhecidiss, que um alcoolatra anonimis. Casamentiss faiz malandris se pirulitá.\n" +
            "\n" +
            "Leite de capivaris, leite de mula manquis sem cabeça. Interessantiss quisso pudia ce receita de bolis, mais bolis eu num gostis. Nullam volutpat risus nec leo commodo, ut interdum diam laoreet. Sed non consequat odio. Praesent vel viverra nisi. Mauris aliquet nunc non turpis scelerisque, eget."
}