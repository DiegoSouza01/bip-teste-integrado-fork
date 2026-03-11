package com.example.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner seedDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM beneficio", Integer.class);

            if (count != null && count == 0) {
                System.out.println("Banco Vazio! Injetando dados padrão para o desafio...");
                jdbcTemplate.update("INSERT INTO beneficio (nome, descricao, valor, ativo, version) VALUES ('Conta Origem', 'Conta Teste 1', 1500.00, true, 0)");
                jdbcTemplate.update("INSERT INTO beneficio (nome, descricao, valor, ativo, version) VALUES ('Conta Destino', 'Conta Teste 2', 800.00, true, 0)");
                System.out.println("Dados injetados com sucesso!");
            }
        };
    }
}