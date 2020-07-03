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
                id = (1..99).random().toString()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString()
            ),
            News(
                title = "Zidane avisa: 'Estais avisado!'",
                tag = "#RealMadrid",
                image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg",
                id = (1..99).random().toString()
            )
        )
    }
}