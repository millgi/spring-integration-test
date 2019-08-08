package com.github.millgi.springintegration.it

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.test.context.MockIntegrationContext
import org.springframework.integration.test.context.SpringIntegrationTest
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [IntegrationConfiguration::class])
@SpringIntegrationTest(noAutoStartup = ["input"])
internal class IntegrationConfigurationTest {

    @Autowired
    private lateinit var mockIntegrationContext: MockIntegrationContext

    @Autowired
    private lateinit var integrationFlow: IntegrationFlow

    @Mock
    private lateinit var receiver: MessageHandler

    @AfterEach
    internal fun tearDown() {
        mockIntegrationContext.resetBeans()
    }

    @Test
    internal fun test_de_test() {
        mockIntegrationContext.substituteMessageHandlerFor("printer", receiver)
        integrationFlow.inputChannel.send(MessageBuilder.withPayload("bye").build())
        val message = ArgumentCaptor.forClass(Message::class.java)
        Mockito.verify(receiver, Mockito.times(1)).handleMessage(message.capture())
        assertThat(message.value.payload).isEqualTo("bye World!")

    }

}