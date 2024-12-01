package org.example.microservicio6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class HealthMonitorController {

    private static final Logger logger = LoggerFactory.getLogger(HealthMonitorController.class);
    private final AtomicInteger patientIdCounter = new AtomicInteger(1);
    private final Random random = new Random();
    private final List<Paciente> allPacientes = new CopyOnWriteArrayList<>();
    private final List<Paciente> urgenciasPacientes = new CopyOnWriteArrayList<>();

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UrgenciasRepository urgenciasRepository;

    @GetMapping("/monitor")
    public Flux<String> monitorHealth() {
        return Flux.interval(Duration.ofSeconds(4))
                .map(tick -> {
                    List<Paciente> pacientes = new CopyOnWriteArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        int id = patientIdCounter.getAndIncrement();
                        int constantesVitales = random.nextInt(201);
                        int pulso = random.nextInt(201);
                        int azucar = random.nextInt(201);
                        Paciente paciente = new Paciente(id, constantesVitales, pulso, azucar);
                        pacientes.add(paciente);
                        allPacientes.add(paciente);
                        pacienteRepository.save(paciente); // Save to Paciente repository
                    }
                    for (Paciente paciente : allPacientes) {
                        paciente.setConstantesVitales(random.nextInt(201));
                        paciente.setPulso(random.nextInt(201));
                        paciente.setAzucar(random.nextInt(201));
                        pacienteRepository.save(paciente); // Update in Paciente repository
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("--------tic ").append(tick).append("------\n");
                    for (Paciente paciente : pacientes) {
                        sb.append("paciente ").append(paciente.getId())
                                .append(": constantes vitales->").append(paciente.getConstantesVitales())
                                .append(", pulso->").append(paciente.getPulso())
                                .append(", azucar->").append(paciente.getAzucar()).append("\n");
                        if (paciente.getConstantesVitales() > 180 || paciente.getPulso() > 150 || paciente.getAzucar() > 180) {
                            sb.append("paciente ").append(paciente.getId()).append(" ha sido llevado a urgencias\n");
                            urgenciasPacientes.add(paciente);
                            allPacientes.remove(paciente);
                            pacienteRepository.delete(paciente); // Delete from Paciente repository
                            Urgencias urgencias = new Urgencias(paciente.getId(), paciente.getConstantesVitales(), paciente.getPulso(), paciente.getAzucar(), 0);
                            urgenciasRepository.save(urgencias); // Save to Urgencias repository
                        }
                    }
                    sb.append("Lista de todos los pacientes:\n");
                    for (Paciente paciente : allPacientes) {
                        sb.append("paciente ").append(paciente.getId())
                                .append(": constantes vitales->").append(paciente.getConstantesVitales())
                                .append(", pulso->").append(paciente.getPulso())
                                .append(", azucar->").append(paciente.getAzucar()).append("\n");
                        if (paciente.getConstantesVitales() > 180 || paciente.getPulso() > 150 || paciente.getAzucar() > 180) {
                            sb.append("paciente ").append(paciente.getId()).append(" ha sido llevado a urgencias\n");
                            urgenciasPacientes.add(paciente);
                            allPacientes.remove(paciente);
                            pacienteRepository.delete(paciente); // Delete from Paciente repository
                            Urgencias urgencias = new Urgencias(paciente.getId(), paciente.getConstantesVitales(), paciente.getPulso(), paciente.getAzucar(), 0);
                            urgenciasRepository.save(urgencias); // Save to Urgencias repository
                        }
                    }
                    logger.info(sb.toString());
                    return sb.toString();
                });
    }

    public List<Paciente> getUrgenciasPacientes() {
        return urgenciasPacientes;
    }

    public void iniciarFlujo() {
        monitorHealth().subscribe();
    }
}