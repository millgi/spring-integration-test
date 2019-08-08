package com.github.millgi.springintegration.it

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.Transformer
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.dsl.*
import org.springframework.integration.handler.MessageProcessor
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.support.MessageBuilder
import java.util.function.Consumer
import java.util.function.Supplier

@Configuration
@EnableIntegration
class IntegrationConfiguration {

    @Bean
    fun supplier(): Supplier<String> = Supplier { "Hello" }

    @Bean
    fun poller(): PollerSpec = Pollers.fixedDelay(1000)

    @Bean
    fun integrationFlow(): IntegrationFlow = IntegrationFlows
            .from(supplier(), Consumer { c -> c.poller(poller()).id("input")})
            .transform(Transformers.converter { message: String -> "$message World!"})
            .handle( MessageHandler { m: Message<*> -> println(m.payload)}, Consumer { c -> c.id("printer")})
            .get()

}
