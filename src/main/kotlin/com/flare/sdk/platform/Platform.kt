package com.flare.sdk.platform

import com.flare.sdk.FlareConfiguration
import java.nio.file.Path


/*
 * Project: sdk
 * Created at: 30/9/24 15:23
 * Created by: Dani-error
 */
interface Platform {

    val platformType: PlatformType
    val dataDirectory: Path

}