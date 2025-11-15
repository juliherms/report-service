package com.github.juliherms.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Classe de configuração para definir um bean de executor com threads virtuais.
 */
@Configuration
public class VirtualThreadExecutorConfig {

    /**
     * Cria um bean para um Executor que utiliza threads virtuais.
     *
     * @return uma instância de Executor com suporte a threads virtuais
     */
    @Bean(name = "virtualThreadExecutor")
    public Executor virtualThreadExecutor() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }
}
