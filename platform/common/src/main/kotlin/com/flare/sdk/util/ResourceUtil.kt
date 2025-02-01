package com.flare.sdk.util

import com.flare.sdk.FlareException
import java.io.*
import java.net.URL
import java.nio.file.Path


/*
 * Project: sdk
 * Created at: 1/2/25 22:56
 * Created by: Dani-error
 */
object ResourceUtil {

    fun getResource(fileName: String): InputStream? {
        try {
            val url: URL? = this.javaClass.classLoader.getResource(fileName)

            if (url == null) {
                return null
            } else {
                val connection = url.openConnection()
                connection.useCaches = false
                return connection.getInputStream()
            }
        } catch (e: IOException) {
            return null
        }
    }

    fun resourceExists(fileName: String): Boolean =
        getResource(fileName) != null

    fun saveResource(base: Path, fileName: String) {
        if (fileName != "") {
            val resourcePath = fileName.replace('\\', '/')
            val `in`: InputStream? = this.getResource(resourcePath)
            requireNotNull(`in`) { "The embedded resource '$resourcePath' cannot be found in the plugin contents." }
            val outFile = File(base.toFile(), resourcePath)
            val lastIndex: Int = resourcePath.lastIndexOf(47.toChar())
            val outDir = File(base.toFile(), resourcePath.substring(0, if (lastIndex >= 0) lastIndex else 0))
            if (!outDir.exists()) {
                outDir.mkdirs()
            }

            try {
                if (!outFile.exists()) {
                    val out: OutputStream = FileOutputStream(outFile)
                    val buf = ByteArray(1024)

                    var len: Int
                    while ((`in`.read(buf).also { len = it }) > 0) {
                        out.write(buf, 0, len)
                    }

                    out.close()
                    `in`.close()
                }
            } catch (ex: IOException) {
                throw FlareException("Could not save ${outFile.name} to $outFile", ex)
            }
        } else {
            throw IllegalArgumentException("ResourcePath cannot be empty")
        }
    }

}