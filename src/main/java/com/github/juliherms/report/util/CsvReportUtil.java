package com.github.juliherms.report.util;

import com.github.juliherms.report.model.Funcionario;
import org.apache.commons.csv.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class CsvReportUtil {

    /**
     * Grava uma lista de objetos Funcionario em um arquivo CSV nomeado de acordo com a região.
     *
     * @param regiao        o nome da região a ser usado no nome do arquivo
     * @param funcionarios  a lista de objetos Funcionario a serem gravados
     * @throws IOException  se ocorrer um erro de entrada/saída
     */
    public static void writeCustomersToCsv(String regiao, List<Funcionario> funcionarios) throws IOException {
        Path path = Paths.get("reports", regiao + "_report.csv");
        Files.createDirectories(path.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(path);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("ID", "Nome", "Email","Genero", "Regiao"))) {

            for (Funcionario funcionario : funcionarios) {
                csvPrinter.printRecord(
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getEmail(),
                        funcionario.getGenero(),
                        funcionario.getRegiao());
            }
        }
    }
}
