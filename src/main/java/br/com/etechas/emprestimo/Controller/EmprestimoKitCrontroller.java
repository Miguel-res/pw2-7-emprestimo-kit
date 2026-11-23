package br.com.etechas.emprestimo.Controller;

import br.com.etechas.emprestimo.Entity.EmprestimoKit;
import br.com.etechas.emprestimo.Repository.EmprestimoKitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprestimo")
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
        if (emprestimoKit.isPresent()) {
            return ResponseEntity.ok(emprestimoKit.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EmprestimoKit>
}
