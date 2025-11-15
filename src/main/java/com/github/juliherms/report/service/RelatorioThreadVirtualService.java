package com.github.juliherms.report.service;

import com.github.juliherms.report.model.Funcionario;
import com.github.juliherms.report.repository.FuncionarioRepository;
import com.github.juliherms.report.util.CsvReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

/**
 * Serviço responsável pela geração de relatórios utilizando threads virtuais.
 */
@Slf4j
@Service
public class RelatorioThreadVirtualService {

    @Autowired
    private FuncionarioRepository repository;

    @Autowired
    private Executor virtualThreadExecutor;

    /**
     * Gera um relatório CSV para uma região específica utilizando threads virtuais.
     *
     * @param regiao a região para a qual o relatório será gerado
     */
    public void gerarRelatorioPorRegiao(String regiao) {
        virtualThreadExecutor.execute(() -> {
            log.info("Gerando relatorio (thread virtual) para a regiao: {} | {}", regiao, Thread.currentThread());
            var funcionarios = repository.findByRegiao(regiao);
            try {
                CsvReportUtil.writeCustomersToCsv("virtual_thread_" + regiao, funcionarios);
            } catch (Exception e) {
                System.out.println("Error writing report for regiao: " + regiao);
            }
        });
    }
}
