package br.com.etechas.emprestimo.Repository;

import br.com.etechas.emprestimo.Entity.EmprestimoKit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprestimoKitRepository extends JpaRepository<EmprestimoKit, Long> {
}
