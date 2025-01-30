package com.flare.sdk.event.impl

import com.flare.sdk.Flare
import com.flare.sdk.event.Event


/*
 * Project: sdk
 * Created at: 30/01/2025 22:10
 * Created by: Dani-error
 */
abstract class PluginEvent(val flare: Flare): Event()

class PluginLoadEvent(flare: Flare) : PluginEvent(flare)

class PluginEnableEvent(flare: Flare) : PluginEvent(flare)

class PluginDisableEvent(flare: Flare) : PluginEvent(flare)