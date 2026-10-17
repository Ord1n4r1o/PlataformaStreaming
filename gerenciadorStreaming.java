import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class gerenciadorStreaming{

	//le os pacotes do arquivo carregando no vetor e retortnando quantos leu.
	public int carregaPacotes(pacote p[], String arquivo){
		int total = 0, quantidade, qtdCanais, codigo, canais[];
		String nome;
		double valor;
		try (BufferedReader lerArquivo = new BufferedReader(new FileReader(arquivo))) {
			quantidade = Integer.parseInt(lerArquivo.readLine());
			while (total < quantidade) {
				codigo = Integer.parseInt(lerArquivo.readLine());
				nome = lerArquivo.readLine();
				valor = Double.parseDouble(lerArquivo.readLine());
				qtdCanais = Integer.parseInt(lerArquivo.readLine());
				canais = new int[qtdCanais];
				for (int i = 0; i < qtdCanais; i++){
					canais[i] = Integer.parseInt(lerArquivo.readLine());
				}
				p[total] = new pacote();
				p[total].cadastraPacote(codigo, nome, valor, qtdCanais, canais);
				total++;
			}
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo: " + e.getMessage());
		}
		return total;
	}

	public int carregaAssinantes(assinante a[], String arquivo){
		int total = 0, quantidade, codigo, codigoPacote;
		String nome, cpf, email;
		double desconto;
		try (BufferedReader lerArquivo = new BufferedReader(new FileReader(arquivo))) {
			quantidade = Integer.parseInt(lerArquivo.readLine());
			while (total < quantidade) {
				codigo = Integer.parseInt(lerArquivo.readLine());
				nome = lerArquivo.readLine();
				cpf = lerArquivo.readLine();
				email = lerArquivo.readLine();
				codigoPacote = Integer.parseInt(lerArquivo.readLine());
				desconto = Double.parseDouble(lerArquivo.readLine());
				a[total] = new assinante();
				a[total].cadastraAssinante(codigo, nome, cpf, email, codigoPacote, desconto);
				total++;
			}
		} catch (IOException e) {
			System.out.println("Erro na abertura do arquivo: " + e.getMessage());
		}
		return total;
	}

    //parte de arquivos começa aqui
	//Grava apenas os pacotes ativos (remocao logica vira remocao real).
	public void salvaPacotes(pacote p[], int total, String arquivo){
		int ativos = 0;
		for (int i = 0; i < total; i++){
			if (p[i].estaAtivo()){
				ativos++;
			}
		}
		try (FileWriter saida = new FileWriter(arquivo);
			BufferedWriter gravarArquivo = new BufferedWriter(saida))
		{
			gravarArquivo.write(String.valueOf(ativos));
			gravarArquivo.newLine();
			for (int i = 0; i < total; i++){
				if (p[i].estaAtivo()){
					gravarArquivo.write(String.valueOf(p[i].retornaCodigo()));
					gravarArquivo.newLine();
					gravarArquivo.write(p[i].retornaNome());
					gravarArquivo.newLine();
					gravarArquivo.write(String.valueOf(p[i].retornaValor()));
					gravarArquivo.newLine();
					gravarArquivo.write(String.valueOf(p[i].retornaQuantidadeCanais()));
					gravarArquivo.newLine();
					for (int j = 0; j < p[i].retornaQuantidadeCanais(); j++){
						gravarArquivo.write(String.valueOf(p[i].retornaCanal(j)));
						gravarArquivo.newLine();
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
	}

	//Grava apenas os assinantes ativos.
	public void salvaAssinantes(assinante a[], int total, String arquivo){
		int ativos = 0;
		for (int i = 0; i < total; i++){
			if (a[i].estaAtivo()){
				ativos++;
			}
		}
		try (FileWriter saida = new FileWriter(arquivo);
			BufferedWriter gravarArquivo = new BufferedWriter(saida))
		{
			gravarArquivo.write(String.valueOf(ativos));
			gravarArquivo.newLine();
			for (int i = 0; i < total; i++){
				if (a[i].estaAtivo()){
					gravarArquivo.write(String.valueOf(a[i].retornaCodigo()));
					gravarArquivo.newLine();
					gravarArquivo.write(a[i].retornaNome());
					gravarArquivo.newLine();
					gravarArquivo.write(a[i].retornaCpf());
					gravarArquivo.newLine();
					gravarArquivo.write(a[i].retornaEmail());
					gravarArquivo.newLine();
					gravarArquivo.write(String.valueOf(a[i].retornaCodigoPacote()));
					gravarArquivo.newLine();
					gravarArquivo.write(String.valueOf(a[i].retornaDesconto()));
					gravarArquivo.newLine();
				}
			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro: " + e.getMessage());
		}
	}


	//Busca pacote por codigo
	public int posicaoPacotePorCodigo(pacote p[], int total, int codigo){
		int posicao = 0;
		boolean achou = false;
		while (!achou && posicao < total){
			if (p[posicao].retornaCodigo() == codigo){
				achou = true;
			} else {
				posicao++;
			}
		}
		if (achou){
			return posicao;
		}
		return -1;
	}

	//Busca do assinante por CPF 
	public int posicaoAssinantePorCpf(assinante a[], int total, String cpf){
		int posicao = 0;
		boolean achou = false;
		while (!achou && posicao < total){
			if (a[posicao].estaAtivo() && a[posicao].retornaCpf().equals(cpf)){
				achou = true;
			} else {
				posicao++;
			}
		}
		if (achou){
			return posicao;
		}
		return -1;
	}


	public void mostraPacotes(pacote p[], int total){
		for (int i = 0; i < total; i++){
			if (p[i].estaAtivo()){
				mostraPacote(p[i]);
			}
		}
	}

	public void mostraPacote(pacote p){
		System.out.println("Codigo: " + p.retornaCodigo());
		System.out.println("Nome: " + p.retornaNome());
		System.out.println("Valor: R$ " + p.retornaValor());
		System.out.println("Canais: " + p.retornaQuantidadeCanais());
		System.out.println("---------------");
	}

	public void mostraAssinantes(assinante a[], int totalA, pacote p[], int totalP){
		for (int i = 0; i < totalA; i++){
			if (a[i].estaAtivo()){
				mostraAssinante(a[i], p, totalP);
			}
		}
	}

	public void mostraAssinante(assinante a, pacote p[], int totalP){
		int pos = posicaoPacotePorCodigo(p, totalP, a.retornaCodigoPacote());
		System.out.println("Codigo: " + a.retornaCodigo());
		System.out.println("Nome: " + a.retornaNome());
		System.out.println("CPF: " + a.retornaCpf());
		System.out.println("E-mail: " + a.retornaEmail());
		if (pos == -1){
			System.out.println("Pacote: sem pacote");
		} else {
			System.out.println("Pacote: " + p[pos].retornaNome());
		}
		System.out.println("Desconto: " + a.retornaDesconto() + "%");
		System.out.println("---------------");
	}

	public void mostraTrechoPacotes(pacote p[], int total, int inicio, int fim){
		int cont = 0;
		for (int i = 0; i < total; i++){
			if (p[i].estaAtivo()){
				cont++;
				if (cont >= inicio && cont <= fim){
					mostraPacote(p[i]);
				}
			}
		}
	}

	public void mostraTrechoAssinantes(assinante a[], int totalA, pacote p[], int totalP, int inicio, int fim){
		int cont = 0;
		for (int i = 0; i < totalA; i++){
			if (a[i].estaAtivo()){
				cont++;
				if (cont >= inicio && cont <= fim){
					mostraAssinante(a[i], p, totalP);
				}
			}
		}
	}

	public void ordenaPacotesPorNome(pacote v[], int total){
		for (int i = 0; i < total - 1; i++){
			for (int j = 0; j < total - i - 1; j++){
				if (v[j].estaAtivo() && v[j+1].estaAtivo() &&
					v[j].retornaNome().compareTo(v[j+1].retornaNome()) > 0){
					pacote aux = v[j];
					v[j] = v[j+1];
					v[j+1] = aux;
				}
			}
		}
	}

	public void ordenaPacotesPorValor(pacote v[], int total){
		for (int i = 0; i < total - 1; i++){
			for (int j = 0; j < total - i - 1; j++){
				if (v[j].estaAtivo() && v[j+1].estaAtivo() &&
					v[j].retornaValor() > v[j+1].retornaValor()){
					pacote aux = v[j];
					v[j] = v[j+1];
					v[j+1] = aux;
				}
			}
		}
	}

	public void ordenaAssinantesPorNome(assinante v[], int total){
		for (int i = 0; i < total - 1; i++){
			for (int j = 0; j < total - i - 1; j++){
				if (v[j].estaAtivo() && v[j+1].estaAtivo() &&
					v[j].retornaNome().compareTo(v[j+1].retornaNome()) > 0){
					assinante aux = v[j];
					v[j] = v[j+1];
					v[j+1] = aux;
				}
			}
		}
	}

	public void ordenaAssinantesPorCpf(assinante v[], int total){
		for (int i = 0; i < total - 1; i++){
			for (int j = 0; j < total - i - 1; j++){
				if (v[j].estaAtivo() && v[j+1].estaAtivo() &&
					v[j].retornaCpf().compareTo(v[j+1].retornaCpf()) > 0){
					assinante aux = v[j];
					v[j] = v[j+1];
					v[j+1] = aux;
				}
			}
		}
	}

	//Gera o proximo codigo livre considerando os removidos.
	public int proximoCodigoPacote(pacote p[], int total){
		int maior = 0;
		for (int i = 0; i < total; i++){
			if (Math.abs(p[i].retornaCodigo()) > maior){
				maior = Math.abs(p[i].retornaCodigo());
			}
		}
		return maior + 1;
	}

	public int proximoCodigoAssinante(assinante a[], int total){
		int maior = 0;
		for (int i = 0; i < total; i++){
			if (Math.abs(a[i].retornaCodigo()) > maior){
				maior = Math.abs(a[i].retornaCodigo());
			}
		}
		return maior + 1;
	}


	public int clientesDoPacote(assinante a[], int total, int codigoPacote){
		int cont = 0;
		for (int i = 0; i < total; i++){
			if (a[i].estaAtivo() && a[i].retornaCodigoPacote() == codigoPacote){
				cont++;
			}
		}
		return cont;
	}
}