package br.com.daniel.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.daniel.model.Pessoa;

@Named("bean") // Se não for definido nenhum nome, por padrão fica o nome da class em
// minúsculo.
@SessionScoped
public class PessoaMB implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Pessoa pessoa;

    private List<Pessoa> pessoas = new ArrayList<>();

    public String adicionar() {

	pessoas.add(pessoa);

	limpar();

	return null;
    }

    private void limpar() {

	pessoa = new Pessoa();

    }

    public void login(ActionEvent event) {
	if (isCPF(pessoa.getCpf())) {
	    System.out.println("CPF " + pessoa.getCpf() + " é válido.");
	} else {
	    System.out.println("CPF " + pessoa.getCpf() + " é inválido.");
	}
    }

    public boolean isCPF(String CPF) {
	// considera-se erro CPF's formados por uma sequencia de numeros iguais
	if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
		|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
		|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
		|| CPF.equals("99999999999") || (CPF.length() != 11))
	    return (false);

	char dig10, dig11;
	int sm, i, r, num, peso;

	// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
	try {
	    // Calculo do 1o. Digito Verificador
	    sm = 0;
	    peso = 10;
	    for (i = 0; i < 9; i++) {
		// converte o i-esimo caractere do CPF em um numero:
		// por exemplo, transforma o caractere '0' no inteiro 0
		// (48 eh a posicao de '0' na tabela ASCII)
		num = (int) (CPF.charAt(i) - 48);
		sm = sm + (num * peso);
		peso = peso - 1;
	    }

	    r = 11 - (sm % 11);
	    if ((r == 10) || (r == 11))
		dig10 = '0';
	    else
		dig10 = (char) (r + 48); // converte no respectivo caractere numerico

	    // Calculo do 2o. Digito Verificador
	    sm = 0;
	    peso = 11;
	    for (i = 0; i < 10; i++) {
		num = (int) (CPF.charAt(i) - 48);
		sm = sm + (num * peso);
		peso = peso - 1;
	    }

	    r = 11 - (sm % 11);
	    if ((r == 10) || (r == 11))
		dig11 = '0';
	    else
		dig11 = (char) (r + 48);

	    // Verifica se os digitos calculados conferem com os digitos informados.
	    if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
		return (true);
	    else
		return (false);
	} catch (InputMismatchException erro) {
	    return (false);
	}
    }

    public static String imprimeCPF(String CPF) {
	return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-"
		+ CPF.substring(9, 11));
    }

    public Pessoa getPessoa() {
	return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
	this.pessoa = pessoa;
    }

    public List<Pessoa> getPessoas() {
	return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
	this.pessoas = pessoas;
    }

}
