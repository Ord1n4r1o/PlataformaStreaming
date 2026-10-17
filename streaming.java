/*
 * Aluno: Emanuel Henrique Rodrigues Ferreira
 * Tema: Sistema para controle de assinatura de servicos de streaming
 * Essa aqui é a classe principal
 */
import java.util.Scanner;

public class streaming{

public static void main(String args[]){
		Scanner lerTeclado = new Scanner(System.in);
		gerenciadorStreaming gerenciador = new gerenciadorStreaming();
		String arqPacotes = "pacotes.txt";
		String arqAssinantes = "assinantes.txt";

		//Carrega os dados dos arquivos para os vetores
		pacote pacotes[] = new pacote[500];
		assinante assinantes[] = new assinante[500];
		int totalPacotes = gerenciador.carregaPacotes(pacotes, arqPacotes);
		int totalAssinantes = gerenciador.carregaAssinantes(assinantes, arqAssinantes);

		boolean alterado = false;
		int opcao, codigo, pos, inicio, fim, qtd, tipo;
		String nome, cpf, email;
		double valor;
		int numeros[];

		// Loop principal do menu 
		menuStreaming meuMenu = new menuStreaming();
		meuMenu.opcao();
		opcao = lerTeclado.nextInt();
		lerTeclado.nextLine();

		while (opcao != 0){
			switch (opcao){

				
				case 1:
					System.out.print("Nome do pacote: ");
					nome = lerTeclado.nextLine();
					System.out.print("Valor (use virgula ex: 9,90): ");
					valor = lerTeclado.nextDouble();
					System.out.print("Quantidade de canais (apenas numeros): ");
					qtd = lerTeclado.nextInt();
					lerTeclado.nextLine();
					numeros = new int[qtd];
					for (int i = 0; i < qtd; i++){
						System.out.print("Digite o numero do canal " + (i+1) + " (apenas numeros): ");
						numeros[i] = lerTeclado.nextInt();
					}
					lerTeclado.nextLine();
					pacotes[totalPacotes] = new pacote();
					pacotes[totalPacotes].cadastraPacote(
						gerenciador.proximoCodigoPacote(pacotes, totalPacotes),
						nome, valor, qtd, numeros);
					totalPacotes++;
					alterado = true;
					System.out.println("Pacote cadastrado!");
					break;

				
				case 2:
					System.out.print("Nome do assinante: ");
					nome = lerTeclado.nextLine();
					System.out.print("CPF: ");
					cpf = lerTeclado.nextLine();
					if (gerenciador.posicaoAssinantePorCpf(assinantes, totalAssinantes, cpf) != -1){
						System.out.println("CPF ja cadastrado!");
					} else {
						System.out.print("E-mail: ");
						email = lerTeclado.nextLine();
						System.out.print("Codigo do pacote: ");
						codigo = lerTeclado.nextInt();
						lerTeclado.nextLine();
						if (gerenciador.posicaoPacotePorCodigo(pacotes, totalPacotes, codigo) == -1){
							System.out.println("Pacote nao encontrado!");
						} else {
							System.out.print("Desconto (%): ");
							valor = lerTeclado.nextDouble();
							lerTeclado.nextLine();
							assinantes[totalAssinantes] = new assinante();
							assinantes[totalAssinantes].cadastraAssinante(
								gerenciador.proximoCodigoAssinante(assinantes, totalAssinantes),
								nome, cpf, email, codigo, valor);
							totalAssinantes++;
							alterado = true;
							System.out.println("Assinante cadastrado!");
						}
					}
					break;

				
				case 3:
					System.out.print("Codigo do pacote a remover: ");
					codigo = lerTeclado.nextInt();
					lerTeclado.nextLine();
					pos = gerenciador.posicaoPacotePorCodigo(pacotes, totalPacotes, codigo);
					if (pos == -1){
						System.out.println("Pacote nao encontrado!");
					} else {
						pacotes[pos].marcaRemovido();
						alterado = true;
						System.out.println("Pacote removido!");
					}
					break;

				
				case 4:
					System.out.print("CPF do assinante a remover: ");
					cpf = lerTeclado.nextLine();
					pos = gerenciador.posicaoAssinantePorCpf(assinantes, totalAssinantes, cpf);
					if (pos == -1){
						System.out.println("Assinante nao encontrado!");
					} else {
						assinantes[pos].marcaRemovido();
						alterado = true;
						System.out.println("Assinante removido!");
					}
					break;

				
				case 5:
					System.out.print("Codigo do pacote: ");
					codigo = lerTeclado.nextInt();
					lerTeclado.nextLine();
					pos = gerenciador.posicaoPacotePorCodigo(pacotes, totalPacotes, codigo);
					if (pos == -1){
						System.out.println("Pacote nao encontrado!");
					} else {
						System.out.print("Novo valor: ");
						valor = lerTeclado.nextDouble();
						lerTeclado.nextLine();
						pacotes[pos].alteraValor(valor);
						alterado = true;
						System.out.println("Valor atualizado!");
					}
					break;

				
				case 6:
					System.out.print("CPF do assinante: ");
					cpf = lerTeclado.nextLine();
					pos = gerenciador.posicaoAssinantePorCpf(assinantes, totalAssinantes, cpf);
					if (pos == -1){
						System.out.println("Assinante nao encontrado!");
					} else {
						System.out.print("Novo e-mail: ");
						email = lerTeclado.nextLine();
						assinantes[pos].alteraEmail(email);
						alterado = true;
						System.out.println("E-mail atualizado!");
					}
					break;

				
				case 7:
					System.out.print("CPF do cliente: ");
					cpf = lerTeclado.nextLine();
					pos = gerenciador.posicaoAssinantePorCpf(assinantes, totalAssinantes, cpf);
					if (pos == -1){
						System.out.println("Cliente nao encontrado!");
					} else {
						int posP = gerenciador.posicaoPacotePorCodigo(
							pacotes, totalPacotes, assinantes[pos].retornaCodigoPacote());
						if (posP == -1){
							System.out.println("Cliente sem pacote.");
						} else {
							System.out.println(assinantes[pos].retornaNome() +
								" tem " + pacotes[posP].retornaQuantidadeCanais() + " canais.");
						}
					}
					break;

				
				case 8:
					System.out.print("Codigo do pacote: ");
					codigo = lerTeclado.nextInt();
					lerTeclado.nextLine();
					pos = gerenciador.posicaoPacotePorCodigo(pacotes, totalPacotes, codigo);
					if (pos == -1){
						System.out.println("Pacote nao encontrado!");
					} else {
						System.out.println("O pacote " + pacotes[pos].retornaNome() +
							" tem " + gerenciador.clientesDoPacote(assinantes, totalAssinantes, codigo) +
							" assinante(s).");
					}
					break;

				
				case 9:
					System.out.print("Codigo do pacote: ");
					codigo = lerTeclado.nextInt();
					lerTeclado.nextLine();
					pos = gerenciador.posicaoPacotePorCodigo(pacotes, totalPacotes, codigo);
					if (pos == -1){
						System.out.println("Pacote nao encontrado!");
					} else {
						System.out.println("Canais do pacote " + pacotes[pos].retornaNome() + ":");
						for (int i = 0; i < pacotes[pos].retornaQuantidadeCanais(); i++){
							System.out.println(pacotes[pos].retornaCanal(i));
						}
					}
					break;

				
				case 10:
					System.out.print("Codigo do pacote: ");
					codigo = lerTeclado.nextInt();
					lerTeclado.nextLine();
					pos = gerenciador.posicaoPacotePorCodigo(pacotes, totalPacotes, codigo);
					if (pos == -1){
						System.out.println("Nao encontrado!");
					} else {
						gerenciador.mostraPacote(pacotes[pos]);
					}
					break;

				// busca do assinante por cpf
				case 11:
					System.out.print("CPF do assinante: ");
					cpf = lerTeclado.nextLine();
					pos = gerenciador.posicaoAssinantePorCpf(assinantes, totalAssinantes, cpf);
					if (pos == -1){
						System.out.println("Nao encontrado!");
					} else {
						gerenciador.mostraAssinante(assinantes[pos], pacotes, totalPacotes);
					}
					break;

				//buscar assinantes por nome 
				case 12:
					System.out.print("Nome (ou parte): ");
					nome = lerTeclado.nextLine();
					int achou = 0;
					for (int i = 0; i < totalAssinantes; i++){
						if (assinantes[i].estaAtivo() &&
							assinantes[i].retornaNome().indexOf(nome) >= 0){
							gerenciador.mostraAssinante(assinantes[i], pacotes, totalPacotes);
							achou++;
						}
					}
					if (achou == 0){
						System.out.println("Nao encontrado!");
					}
					break;

		
				case 13:
					gerenciador.ordenaPacotesPorNome(pacotes, totalPacotes);
					gerenciador.mostraPacotes(pacotes, totalPacotes);
					break;

				
				case 14:
					gerenciador.ordenaPacotesPorValor(pacotes, totalPacotes);
					gerenciador.mostraPacotes(pacotes, totalPacotes);
					break;

				
				case 15:
					gerenciador.ordenaAssinantesPorNome(assinantes, totalAssinantes);
					gerenciador.mostraAssinantes(assinantes, totalAssinantes, pacotes, totalPacotes);
					break;

				
				case 16:
					gerenciador.ordenaAssinantesPorCpf(assinantes, totalAssinantes);
					gerenciador.mostraAssinantes(assinantes, totalAssinantes, pacotes, totalPacotes);
					break;

				
				case 17:
					System.out.println("1 - Pacotes / 2 - Assinantes");
					tipo = lerTeclado.nextInt();
					lerTeclado.nextLine();
					if (tipo == 1){
						gerenciador.mostraPacotes(pacotes, totalPacotes);
					} else {
						gerenciador.mostraAssinantes(assinantes, totalAssinantes, pacotes, totalPacotes);
					}
					break;

				
				case 18:
					System.out.println("1 - Pacotes / 2 - Assinantes");
					tipo = lerTeclado.nextInt();
					lerTeclado.nextLine();
					System.out.print("Do elemento: ");
					inicio = lerTeclado.nextInt();
					System.out.print("Ate o elemento: ");
					fim = lerTeclado.nextInt();
					lerTeclado.nextLine();
					if (tipo == 1){
						gerenciador.mostraTrechoPacotes(pacotes, totalPacotes, inicio, fim);
					} else {
						gerenciador.mostraTrechoAssinantes(assinantes, totalAssinantes, pacotes, totalPacotes, inicio, fim);
					}
					break;

		
				case 19:
					gerenciador.salvaPacotes(pacotes, totalPacotes, arqPacotes);
					gerenciador.salvaAssinantes(assinantes, totalAssinantes, arqAssinantes);
					alterado = false;
					System.out.println("Alteracoes gravadas!");
					break;

				default:
					System.out.println("Opcao invalida!");
					break;
			}

			meuMenu.opcao();
			opcao = lerTeclado.nextInt();
			lerTeclado.nextLine();
		}

		// Ao sair pergunta se quer gravar o que ficou pra tras/pendente
		if (alterado){
			System.out.print("Existem alteracoes nao gravadas. Gravar? (S/N): ");
			String resposta = lerTeclado.nextLine();
			if (resposta.equals("S") || resposta.equals("s")){
				gerenciador.salvaPacotes(pacotes, totalPacotes, arqPacotes);
				gerenciador.salvaAssinantes(assinantes, totalAssinantes, arqAssinantes);
				System.out.println("Alteracoes gravadas!");
			}
		}
		lerTeclado.close();
	}
}