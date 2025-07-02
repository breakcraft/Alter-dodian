package org.alter.game.metrics

import gg.rsmod.util.ServerProperties
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.metrics.micrometer.*
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.alter.game.Server
import org.alter.game.model.World
import org.alter.game.service.Service

class MetricsService : Service {
    private lateinit var registry: PrometheusMeterRegistry

    override fun init(server: Server, world: World, serviceProperties: ServerProperties) {
        registry = PrometheusMeterRegistry(io.micrometer.prometheus.PrometheusConfig.DEFAULT)
        embeddedServer(Netty, port = serviceProperties.getOrDefault("metrics-port", 8081)) {
            install(MicrometerMetrics) { this.registry = this@MetricsService.registry }
            routing {
                get("/prometheus-metrics") { call.respondText(registry.scrape()) }
            }
        }.start(wait = false)
    }
}
