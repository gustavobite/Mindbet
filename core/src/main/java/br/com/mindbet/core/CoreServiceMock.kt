package br.com.mindbet.core

import br.com.mindbet.core.news.model.News
import kotlinx.coroutines.delay

class CoreServiceMock: CoreService{
    override suspend fun getNews(): List<News> {
        delay(2000)
        return listOf(
            News(title = "Zidane avisa: 'Estais avisado!'", tag = "#RealMadrid", image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg", id= (1..99).random().toString()),
            News(title = "Zidane avisa: 'Estais avisado!'", tag = "#RealMadrid", image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg", id= (1..99).random().toString()),
            News(title = "Zidane avisa: 'Estais avisado!'", tag = "#RealMadrid", image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg", id= (1..99).random().toString()),
            News(title = "Zidane avisa: 'Estais avisado!'", tag = "#RealMadrid", image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg", id= (1..99).random().toString()),
            News(title = "Zidane avisa: 'Estais avisado!'", tag = "#RealMadrid", image = "https://pbs.twimg.com/media/CdCsSbAXEAA2KH_.jpg", id= (1..99).random().toString()))
    }
}