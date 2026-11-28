package br.com.etechas.emprestimo.Entity;

import br.com.etechas.emprestimo.Enums.StatusEnum;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.aspectj.bridge.IMessage;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoKit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_emprestimo")
    private int id;
    @Column(name="codigo_kit")
    private String codigoKit;
    @Column(name="nome_aluno")
    private String nomeAluno;
    @Column(name="horarioRetirada")
    private LocalTime horarioRetirada;
    @Column(name="horarioLimiteDevolucao")
    private LocalTime horarioLimiteDevolucao;
    @Column(name="horarioDevolucaoReal")
    private LocalTime horarioDevolucaoReal;
    @Column(name="status_kit")
    private StatusEnum status;
}
