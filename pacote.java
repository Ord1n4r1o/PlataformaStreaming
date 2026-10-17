public class pacote{
	private int codigo;
	private String nome;
	private double valor;
	private int quantidadeCanais;
	private int canais[];

	public void cadastraPacote(int c, String n, double v, int q, int numeros[]){
		codigo = c;
		nome = n;
		valor = v;
		quantidadeCanais = q;
		canais = numeros;
	}

	public void alteraValor(double v){
		valor = v;
	}

	public void marcaRemovido(){
		//Usei esse comando de math pro "codigo" ser sempre negativo ou zero
		codigo = -Math.abs(codigo);
	}

	public boolean estaAtivo(){
		return codigo > 0;
	}

	public int retornaCodigo(){
		return codigo;
	}

	public String retornaNome(){
		return nome;
	}

	public double retornaValor(){
		return valor;
	}

	public int retornaQuantidadeCanais(){
		return quantidadeCanais;
	}

	public int retornaCanal(int i){
		return canais[i];
	}
}