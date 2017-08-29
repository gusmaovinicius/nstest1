# nsservices

<h2>Tecnologias Utilizadas </h2>

Devido aos requisitos não funcionais descritos, fui encorajado a tentar realizar este desafio utilizando um pouco da programação reativa e um banco não relacional. Para este próposito, o projeto foi realizado com Spring Boot 2 com Spring Framework 5.0, utilizando Banco de dados MongoDB e servidor Netty.

<h2>Como executar o projeto</h2>

1- Banco de dados
<br/>
Você precisa ter um banco com o nome test no MongoDB.

2- Serviço Campaign

Para rodar o serviço execute os comandos:

gradlew clean build
<br/>
java -jar build/libs/Campaign.jar --server.port=9000

O serviço rodará no endereço http://localhost:9000/api/campaign

3- Serviço PartnerFan

gradlew clean build
<br/>
java -jar build/libs/PartnerFan.jar --server.port=9001

O serviço rodará no endereço http://localhost:9001/api/partnerfan

<h2>Mocks de Testes</h2>
Os mocks foram realizados utilizando o Postman e o teste completo está disponível em https://documenter.getpostman.com/view/2415521/nstest/6n61tZR

<h2>Resposta 3</h2>

Obs.: Eu tive um problema com meu plugin Egit e precisei colocar o código em https://github.com/gusmaovinicius/nsttest2

<code>
public class StreamWork implements IVstream{
	
	private Stream<Character> stream;
	private Iterator<Character> iterator;
	private int aCount;
	private int eCount;
	private int iCount;
	private int oCount;
	private int uCount;
	private char last;
	private char beforeLast;
	
	public StreamWork(Stream<Character> stream){
		this.stream = stream;
		this.iterator = stream.iterator();
	}
	
	@Override
	public Boolean hasNext() {		
		return iterator.hasNext();
	}

	@Override
	public char getNext() {
		return this.iterator.next();
	}	
	
	
	public char firstChar(Stream input) { 
		String consonants = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";
		String vowels = "aeiouAEIOU";
		char tempChar = '\u0000';
		while(hasNext()){
		
			char current = getNext();
			//boolean hasNext =  hasNext();
			
			if(current == 'a'){		
				aCount++;
				if(aCount == 1 
						&& consonants.contains(String.valueOf(last)) 
						&& vowels.contains(String.valueOf(beforeLast)));
					tempChar = current;
			}
			
			else if(current == 'e'){
				eCount++;
				if(eCount == 1 
						&& consonants.contains(String.valueOf(last)) 
						&& vowels.contains(String.valueOf(beforeLast)));
					tempChar = current;
			}
			
			else if(current == 'i'){		
				iCount++;
				if(iCount == 1 
						&& consonants.contains(String.valueOf(last)) 
						&& vowels.contains(String.valueOf(beforeLast)));
					tempChar = current;				
			}
			
			else if(current == 'o'){		
				oCount++;
				if(oCount == 1 
						&& consonants.contains(String.valueOf(last)) 
						&& vowels.contains(String.valueOf(beforeLast)));
					tempChar = current;
			}
			
			else if(current == 'u'){		
				uCount++;
				if(uCount == 1 
						&& consonants.contains(String.valueOf(last)) 
						&& vowels.contains(String.valueOf(beforeLast)));
					tempChar = current;
			}
			beforeLast = last;
			last = current;
		}
		return tempChar;		
	}
	
	public static void main(String[] args) {		
		
		Stream<Character> chars = "aAbBABacafe".chars().mapToObj(i -> (char)i);
		
		StreamWork ws = new StreamWork(chars);
		
		System.out.println(ws.firstChar(chars));
		
	}

}
</code>
