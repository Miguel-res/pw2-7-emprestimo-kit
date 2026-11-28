package br.com.etechas.emprestimo.Controller;

import br.com.etechas.emprestimo.Entity.EmprestimoKit;
import br.com.etechas.emprestimo.Enums.StatusEnum;
import br.com.etechas.emprestimo.Repository.EmprestimoKitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/kits-maker")
public class EmprestimoKitCrontroller {

    @Autowired
    private EmprestimoKitRepository emprestimoKitRepository;

    @GetMapping
    public ResponseEntity<List<EmprestimoKit>> listarEmprestimoKit() {
        return ResponseEntity.ok(emprestimoKitRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoKit> buscarPorId(@PathVariable long id) {
        var emprestimoKit = emprestimoKitRepository.findById(id);
        if (emprestimoKit.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(emprestimoKit.get());
    }

    @PostMapping
    public ResponseEntity<EmprestimoKit> cadastrar(@RequestBody EmprestimoKit dados) {
        if (dados.getCodigoKit() == null || dados.getCodigoKit().isBlank() || dados.getCodigoKit().length() > 255) {
            return ResponseEntity.badRequest().build();
        }
        if (dados.getNomeAluno() == null || dados.getNomeAluno().isBlank() || dados.getNomeAluno().length() > 255) {
            return ResponseEntity.badRequest().build();
        }

        var agora = LocalTime.now().withNano(0);


        var novoEmprestimo = EmprestimoKit.builder()
                .codigoKit(dados.getCodigoKit())
                .nomeAluno(dados.getNomeAluno())
                .horarioRetirada(agora)
                .horarioLimiteDevolucao(agora.plusHours(2))
                .status(StatusEnum.EM_USO)
                .build();

        var salvo = emprestimoKitRepository.save(novoEmprestimo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoKit> atualizar(@RequestBody EmprestimoKit dados,
                                                   @PathVariable Long id) {
        if (dados.getCodigoKit() == null || dados.getCodigoKit().isBlank() || dados.getCodigoKit().length() > 255) {
            return ResponseEntity.badRequest().build();
        }
        if (dados.getNomeAluno() == null || dados.getNomeAluno().isBlank() || dados.getNomeAluno().length() > 255) {
            return ResponseEntity.badRequest().build();
        }

        var existe = emprestimoKitRepository.findById(id);
        if (existe.isPresent()) {
            var emprestimo = existe.get();
            emprestimo.setCodigoKit(dados.getCodigoKit());
            emprestimo.setNomeAluno(dados.getNomeAluno());
            var atualizado = emprestimoKitRepository.save(emprestimo);
            return ResponseEntity.ok(atualizado);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        var existe = emprestimoKitRepository.findById(id);
        if (existe.isPresent()) {
            emprestimoKitRepository.delete(existe.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoKit> devolver(@PathVariable Long id) {
        var existe = emprestimoKitRepository.findById(id);
        if (existe.isPresent()) {
            var emprestimo = existe.get();

            if (emprestimo.getStatus() != StatusEnum.EM_USO) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            emprestimo.setHorarioDevolucaoReal(LocalTime.now().withNano(0));

            if (emprestimo.getHorarioDevolucaoReal().isAfter(emprestimo.getHorarioLimiteDevolucao())) {
                emprestimo.setStatus(StatusEnum.DEVOLVIDO_COM_ATRASO);
            } else {
                emprestimo.setStatus(StatusEnum.DEVOLVIDO_NO_PRAZO);
            }

            var devolvido = emprestimoKitRepository.save(emprestimo);
            return ResponseEntity.ok(devolvido);
        }
        return ResponseEntity.notFound().build();
    }

}
