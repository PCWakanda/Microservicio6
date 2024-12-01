package org.example.microservicio6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class HealthMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(HealthMonitorController.class);
    private final AtomicInteger patientIdCounter = new AtomicInteger(1);
    private final Random random = new Random();

    @GetMapping("/monitor")
    public Flux<String> monitorHealth() {
        return Flux.interval(Duration.ofSeconds(4))
                .map(tick -> {
                    List<Paciente> pacientes = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        int id = patientIdCounter.getAndIncrement();
                        int constantesVitales = random.nextInt(201);
                        int pulso = random.nextInt(201);
                        int azucar = random.nextInt(201);
                        Paciente paciente = new Paciente(id, constantesVitales, pulso, azucar);
                        pacientes.add(paciente);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("--------tic ").append(tick).append("------\n");
                    for (Paciente paciente : pacientes) {
                        sb.append("paciente ").append(paciente.getId())
                                .append(": constantes vitales->").append(paciente.getConstantesVitales())
                                .append(", pulso->").append(paciente.getPulso())
                                .append(", azucar->").append(paciente.getAzucar()).append("\n");
                        if (paciente.getConstantesVitales() > 150 && paciente.getPulso() > 100 && paciente.getAzucar() > 100) {
                            sb.append("paciente ").append(paciente.getId()).append(" ha sido llevado a urgencias\n");
                        }
                    }
                    logger.info(sb.toString());
                    return sb.toString();
                });
    }

    public void iniciarFlujo() {
        monitorHealth().subscribe();
    }
}