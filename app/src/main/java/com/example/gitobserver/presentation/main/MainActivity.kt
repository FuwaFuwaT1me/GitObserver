package com.example.gitobserver.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gitobserver.R
import com.example.gitobserver.domain.usecase.SharedPrefLanguageColorsStorageUseCase
import com.example.gitobserver.domain.usecase.SharedPrefUserStorageUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.io.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefUserStorageUseCase: SharedPrefUserStorageUseCase

    @Inject
    lateinit var sharedPrefLanguageColorsStorageUseCase: SharedPrefLanguageColorsStorageUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setLanguageColors()
    }

    private fun setLanguageColors() {
        if (sharedPrefLanguageColorsStorageUseCase.getLanguageColors() == "") {
            val inputStream: InputStream = assets.open("colors.json")
            val writer: Writer = StringWriter()
            val buffer = CharArray(1024)
            inputStream.use { stream ->
                val reader: Reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
                var n: Int
                while (reader.read(buffer).also { n = it } != -1) {
                    writer.write(buffer, 0, n)
                }
            }

            sharedPrefLanguageColorsStorageUseCase.saveLanguageColors(writer.toString())
        }
    }
}