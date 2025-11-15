package com.github.juliherms.report.service;

import com.github.juliherms.report.model.Funcionario;
import com.github.juliherms.report.repository.FuncionarioRepository;
import com.github.juliherms.report.util.CsvReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Serviço responsável pela geração de relatórios da plataforma.
 * Utiliza um pool fixo de threads para processar múltiplas requisições de geração de relatórios simultaneamente.
 */
@Slf4j
@Service
public class PlatformReportService {

    private static final int MAX_THREADS = 5;

    @Autowired
    private FuncionarioRepository repository;

    Executor executor = Executors.newFixedThreadPool(MAX_THREADS);

    /**
     * Gera um relatório CSV para uma região específica utilizando threads do pool.
     *
     * @param regiao a região para a qual o relatório será gerado
     */
    public void gerarRelatorioPorRegion(String regiao) {
        // executa a tarefa de geração de relatório em uma thread separada
        executor.execute(() -> {
            log.info("gerando relatorio(platform) para a regiao: {} | {}", regiao, Thread.currentThread());
            List<Funcionario> funcionarios = repository.findByRegiao(regiao);
            try {
                CsvReportUtil.writeCustomersToCsv("platform_" + regiao, funcionarios);
            } catch (Exception e) {
                System.out.println("Error writing report for regiao: " + regiao);
            }
        });
    }
}
