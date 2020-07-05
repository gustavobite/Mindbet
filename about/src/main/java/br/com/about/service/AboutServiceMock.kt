package br.com.about.service

import android.content.Context
import br.com.about.R
import br.com.about.model.AboutUsResponse
import br.com.about.model.Member
import kotlinx.coroutines.delay
import retrofit2.Response

class AboutServiceMock(private val context: Context) : AboutService {

    override suspend fun getAboutUs(): Response<AboutUsResponse> {
        return Response.success(AboutUsResponse(context.resources.getString(R.string.about)))
    }

    override suspend fun getMembers(): Response<List<Member>> {
        return Response.success(listOf(
            Member(
                name = "Luanel Messi",
                job = "Desenvolvedor",
                image = null
            ),
            Member(
                name = "Gu",
                job = "Investidor",
                image = null
            ),
            Member(
                name = "Praia Judeu",
                job = "Desenvolvedor",
                image = null
            ),
            Member(
                name = "Tereza Brandt",
                job = "Mascote",
                image = null
            ),
            Member(
                name = "Pithon",
                job = "Viajante",
                image = null
            )
        ))
    }
}