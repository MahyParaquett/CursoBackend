package br.com.teste.primeirorest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.primeirorest.compartilhado.AnimalDto;
import br.com.teste.primeirorest.compartilhado.PessoaDto;
import br.com.teste.primeirorest.http.AnimaisFeignClient;
import br.com.teste.primeirorest.model.Pessoa;
import br.com.teste.primeirorest.repository.PessoaRepository;

@Service
public class PessoaServiceImpl implements PessoaService {
    @Autowired
    private PessoaRepository repo;
    
    @Autowired
    private AnimaisFeignClient animaisMsClient;

    @Override
    public PessoaDto criarPessoa(PessoaDto pessoa) {
        return salvarPessoa(pessoa);
    }

    @Override
    public List<PessoaDto> obterTodos() {
        List<Pessoa> pessoas = repo.findAll();

        return pessoas.stream()
            .map(pessoa -> new ModelMapper().map(pessoa, PessoaDto.class))
            .collect(Collectors.toList());
    }

    @SuppressWarnings("null")
    @Override
    public Optional<PessoaDto> obterPorId(Integer id) {
       Optional<Pessoa> pessoa = repo.findById(id);

       if(pessoa.isPresent()) {

            PessoaDto dto = new ModelMapper().map(pessoa.get(), PessoaDto.class);

            //Aqui pegar os animais da pessoa pelo id
            List<AnimalDto> animais = animaisMsClient.obterAnimais(id);
            //Aqui adiciono a lista de animais na pessoaDto
            dto.setAnimais(animais);

            return Optional.of(dto);
       }

       return Optional.empty();
    }

    @SuppressWarnings("null")
    @Override
    public void removerPessoa(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public PessoaDto atualizarPessoa(Integer id, PessoaDto pessoa) {
        pessoa.setId(id);
        return salvarPessoa(pessoa);
    }
    @SuppressWarnings("null")
    private PessoaDto salvarPessoa(PessoaDto pessoa) {
        ModelMapper mapper = new ModelMapper();
        Pessoa pessoaEntidade = mapper.map(pessoa, Pessoa.class);
        pessoaEntidade = repo.save(pessoaEntidade);

        return mapper.map(pessoaEntidade, PessoaDto.class);
    }
}
