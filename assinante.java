public class assinante{
	private int codigo;
	private String nome, cpf, email;
	private int codigoPacote;
	private double desconto;

	public void cadastraAssinante(int c, String n, String cp, String e, int codP, double d){
		codigo = c;
		nome = n;
		cpf = cp;
		email = e;
		codigoPacote = codP;
		desconto = d;
	}

	public void alteraPacote(int codP){
		codigoPacote = codP;
	}

	public void alteraEmail(String e){
		email = e;
	}

	public void marcaRemovido(){
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

	public String retornaCpf(){
		return cpf;
	}

	public String retornaEmail(){
		return email;
	}

	public int retornaCodigoPacote(){
		return codigoPacote;
	}

	public double retornaDesconto(){
		return desconto;
	}
}