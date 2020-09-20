package br.estudos.estudos.buscasBanco;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import br.estudos.estudos.entity.Pessoa;

public class UsuarioBusca {

    private String nome;

    public UsuarioBusca(String nome) {
        this.setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Specification<Pessoa> toSpec() {
        return (root, query, builder) -> {
            List<Predicate> predicados = new ArrayList<>();
            if (StringUtils.hasText(nome)) {
                Path<String> campoNome = root.<String>get("nome");
                Predicate predicadoNome = builder.like(campoNome, "%" + nome + "%");
                predicados.add(predicadoNome);
            }
            return builder.and(predicados.toArray(new Predicate[0]));
        };
    }

}
