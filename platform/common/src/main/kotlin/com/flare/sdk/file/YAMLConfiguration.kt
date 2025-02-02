package com.flare.sdk.file

import java.io.File


/*
 * Project: sdk
 * Created at: 1/2/25 22:28
 * Created by: Dani-error
 */
abstract class YAMLConfiguration(file: File) : ConfigurationSection {

    abstract fun save()

}