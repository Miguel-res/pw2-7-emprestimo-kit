package br.com.etechas.emprestimo.Entity;

import br.com.etechas.emprestimo.Enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="EmprestimoKit")
public class EmprestimoKit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_emprestimo")
    private Long id;
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
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}
