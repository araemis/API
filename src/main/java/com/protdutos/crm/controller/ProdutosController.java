package com.protdutos.crm.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.protdutos.crm.model.Produto;
import com.protdutos.crm.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping
	public List<Produto> listar() {
		return produtoRepository.findAll();
	}

	@PostMapping
	public Produto add(HttpServletResponse response, @RequestBody Produto produto) {
		if (produtoRepository.existsById(produto.getCodigo())) {
			response.setStatus(400);
		} else {
			response.setStatus(201);
			return produtoRepository.save(produto);
		}
		return null;
	}

	@GetMapping
	@RequestMapping(value = "/{codigo}")
	public Optional<Produto> findByIdIs(HttpServletResponse response, @PathVariable(value = "codigo") Integer codigo) {
		if (produtoRepository.existsById(codigo)) {
			response.setStatus(200);
			return produtoRepository.findById(codigo);
		} else {
			response.setStatus(404);
		}
		return null;

	}

	@PutMapping(value = "/{codigo}")
	public Optional<Object> replaceproduto(HttpServletResponse response, @RequestBody Produto newproduto,
			@PathVariable(value = "codigo") Integer codigo) {
		if (produtoRepository.existsById(codigo)) {
			return produtoRepository.findById(codigo).map(produto -> {
				response.setStatus(200);
				produto.setNome(newproduto.getNome());
				produto.setPreco(newproduto.getPreco());
				produto.setMarca(newproduto.getMarca());
				return produtoRepository.save(produto);
			});
		} else {
			response.setStatus(400);
		}
		return null;
	}

	@DeleteMapping(value = "/{codigo}")
	public void deleteEmployee(HttpServletResponse response, @PathVariable(value = "codigo") Integer codigo) {
		if (produtoRepository.existsById(codigo)) {
			produtoRepository.deleteById(codigo);
			response.setStatus(200);
		} else {
			response.setStatus(400);
		}
	}
}
