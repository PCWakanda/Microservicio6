package org.example.microservicio6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class UrgenciasController {

    private static final Logger logger = LoggerFactory.getLogger(UrgenciasController.class);
    private final List<Paciente> urgenciasPacientes;

    @Autowired
    private UrgenciasRepository urgenciasRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public UrgenciasController(HealthMonitorController healthMonitorController) {
        this.urgenciasPacientes = healthMonitorController.getUrgenciasPacientes();
    }

    public Flux<String> urgencias() {
        return Flux.interval(Duration.ofSeconds(4))
                .map(tick -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("--------tic urgencias ").append(tick).append("------\n");
                    List<Paciente> toRemove = new CopyOnWriteArrayList<>();
                    for (Paciente paciente : urgenciasPacientes) {
                        paciente.incrementarTicksEnUrgencias(); // Increment ticks in urgencias
                        sb.append("paciente en urgencias ").append(paciente.getId())
                                .append(": constantes vitales->").append(paciente.getConstantesVitales())
                                .append(", pulso->").append(paciente.getPulso())
                                .append(", azucar->").append(paciente.getAzucar())
                                .append(", ticks en urgencias->").append(paciente.getTicksEnUrgencias()).append("\n");
                        if (paciente.getTicksEnUrgencias() == 2) {
                            sb.append("paciente ").append(paciente.getId()).append(" se ha curado\n");
                            toRemove.add(paciente);
                        }
                        // Save to Urgencias repository
                        Urgencias urgencias = new Urgencias(paciente.getId(), paciente.getConstantesVitales(), paciente.getPulso(), paciente.getAzucar(), paciente.getTicksEnUrgencias());
                        urgenciasRepository.save(urgencias);
                    }
                    urgenciasPacientes.removeAll(toRemove);
                    for (Paciente paciente : toRemove) {
                        urgenciasRepository.deleteById(paciente.getId()); // Delete from Urgencias repository
                        pacienteRepository.save(paciente); // Save cured patient back to Paciente repository
                    }
                    logger.info(sb.toString());
                    return sb.toString();
                });
    }

    public void iniciarFlujo() {
        urgencias().subscribe();
    }
}