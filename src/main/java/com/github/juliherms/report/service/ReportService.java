package com.github.juliherms.report.service;

import com.github.juliherms.report.model.Funcionario;
import com.github.juliherms.report.repository.FuncionarioRepository;
import com.github.juliherms.report.util.CsvReportUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável pela geração de relatórios.
 */
@Slf4j
@Service
public class ReportService {

    @Autowired
    private FuncionarioRepository repository;


    //300
    //tomcat default thread 200
    //200 request processing
    //100 request waiting in queue
    /**
     * Gera um relatório CSV para uma região específica.
     *
     * @param regiao a região para a qual o relatório será gerado
     */
    public void gerarRelatorioPorRegion(String regiao) {
        log.info("gerando relatorio para a regiao: {} | {}", regiao, Thread.currentThread());
        List<Funcionario> funcionarios = repository.findByRegiao(regiao);//1
        try {
            CsvReportUtil.writeCustomersToCsv("relatorio_" + regiao, funcionarios);//2
        } catch (Exception e) {
            System.out.println("Error writing report for regiao: " + regiao);
        }
    }
}
