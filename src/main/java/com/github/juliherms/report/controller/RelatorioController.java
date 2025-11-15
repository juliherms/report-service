package com.github.juliherms.report.controller;

import com.github.juliherms.report.service.PlatformReportService;
import com.github.juliherms.report.service.RelatorioThreadVirtualService;
import com.github.juliherms.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PlatformReportService platformReportService;

    @Autowired
    private RelatorioThreadVirtualService relatorioThreadVirtualService;

    @PostMapping("/{regiao}")
    public ResponseEntity<String> gerarRelatorio(@PathVariable String regiao) {
        reportService.gerarRelatorioPorRegion(regiao);
        return ResponseEntity.ok("Relatório gerado para a região: " + regiao);
    }

    @PostMapping("/platform/{regiao}")
    public ResponseEntity<String> gerarRelatorioPlatform(@PathVariable String regiao) {
        platformReportService.gerarRelatorioPorRegion(regiao);
        return ResponseEntity.ok("Platform relatório gerado para a região: " + regiao);
    }

    @PostMapping("/virtual/{regiao}")
    public ResponseEntity<String> gerarRelatorioVirutalThread(@PathVariable String regiao) {
        relatorioThreadVirtualService.gerarRelatorioPorRegiao(regiao);
        return ResponseEntity.ok("Platform relatório gerado para a região: " + regiao);
    }
}
