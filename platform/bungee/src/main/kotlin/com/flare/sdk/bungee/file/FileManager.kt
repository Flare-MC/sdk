package com.flare.sdk.bungee.file

import com.flare.sdk.file.AbstractFileManager
import com.flare.sdk.file.YAMLConfiguration
import com.flare.sdk.platform.Platform
import com.flare.sdk.util.ResourceUtil
import java.io.File


/*
 * Project: sdk
 * Created at: 1/2/25 22:39
 * Created by: Dani-error
 */
class FileManager(platform: Platform) : AbstractFileManager(platform.dataDirectory) {

    override fun getYAML(file: File): YAMLConfiguration =
        BungeeYAMLFile(file)

    override fun resourceExists(fileName: String): Boolean =
        ResourceUtil.getResource(fileName) != null

    override fun saveResource(fileName: String) =
        ResourceUtil.saveResource(base, fileName)

}