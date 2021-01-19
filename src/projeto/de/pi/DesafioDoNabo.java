package projeto.de.pi; // Pacote do projeto.

import java.util.Random;  // Importação dos pacotes contendo as 
import java.util.Scanner; // funcionalidades de utilidades do jogo.

/* Projeto da Disciplina de Processamento da Informção
 *  Este projeto de como tema a implementação de um pequeno jogo em estilo
 *  RPG (Role-playing game) onde o jogador irá experimentar um dia da vida
 *  corrida dos estudantes da faculdade UFABC.
 *
 *  O nome do projeto vem justamente do jargão utilizado pelos estudantes
 *  para se referir os seus problemas na faculdade, os vários "nabos" que há.
 */
public class DesafioDoNabo {
    /* Utilitarios do programa.
     *  sc:  Objeto do tipo Scanner para recolher dados do usuário.
     *  rnd: Objeto do tipo Random para a geração de números aleatórios.
     */
    public static Scanner sc = new Scanner(System.in);
    public static Random rnd = new Random();

    /* Dados do personagem:
     *  perNome: Nome do personagem.
     *  perPos: Valor número da posição do personagem no mapa.
     *  perTemp: Temperamento do personagem.
     *  perEstu: Nível de estudos do personagem.
     */
    public static String perNome;
    public static int perPos = 0;
    public static int perFeli = 5;
    public static int perEstu = 0;
    
    // TODO: Acrescentar novos status para o personagem,
    //       como nível de energia (sono), fome, entre outros.

    /* Controle do tempo do jogo.
     *  tmpMaxHora: Número máximo da hora no jogo.
     *  tmpMaxDia:  Número máximo do dia no jogo.
     *  tmpMinAula: Horário da primeira aula.
     *  tmpMedAula: Horário do fim da primeira aula e inicio da segunda.
     *  tmpMaxAula: Horário do fim da segunda aula. 
     *  tmpHora: Hora atual no jogo.
     *  tmpDia:  Dia atual de jogo.
     */
    public static double tmpMaxHora = 24;
    public static double tmpMaxDia = 1;

    public static double tmpMinAula = 19;
    public static double tmpMedAula = 20.30;
    public static double tmpMaxAula = 22.30;

    public static double tmpHora = 8;
    public static int tmpDia = 1;
    
    /* Controle de sincronia do jogo.
     *  rodando: Variavel booleana que verifica se o
     *           jogo pode continuar a rodar ou não.
     */
    public static boolean rodando = true;
    
    // TODO: Acrescentar um sistema de habilidades do personagem,
    //       como inteligência emocinal e racional, entre outros.

    /* Dados dos locais do jogo.
     *  locMax:  Número máximo de locais no jogo.
     *  locNome: Vetor para armazenar o nome dos locais.
     *   [Dimensão 1]: Número do local.
     *  locMov: Matriz dimensional booleana para armazenar a possibilidade
     *          de movimentação entre dois locais quaisquer.
     *   [Dimensão 1]: Local de saída do usuário.
     *   [Dimensão 2]: Local de entrada do usuário.
     */
    public static final int locMax = 6;
    public static String[] locNome = new String[locMax];
    public static boolean[][] locMov = new boolean[locMax][locMax];

    /* Dados das atividades do jogo.
     *  ativMax:  Número máximo de atividades para cada local no mapa.
     *  ativNome: Nome da atividade.
     *   [Dimensão 1]: Local da atividade.
     *   [Dimensão 2]: Número da atividade.
     *  ativQnt:  Informa a quantidade de atividades para cada local.
     *  ativHora: Informa o horário das atividades.
     *   [Dimensão 1]: Local da atividade.
     *   [Dimensão 2]: Número da atividade.
     *   [Dimensão 3]: Horário da atividade, [0] para o horário de íncio
     *                 e [1] para o horário de termino da atividade.
     *  ativAula: Informa se a atividade é uma aula ou não.
     *   [Dimensão 1]: Local da atividade.
     *   [Dimensão 2]: Número da atividade.
     */
    public static final int ativMax = 6;
    public static String[][] ativNome = new String[locMax][ativMax];
    public static int[] ativQnt = new int[ativMax];
    public static double[][][] ativHora = new double[locMax][ativMax][2];
    public static boolean[][] ativAula = new boolean[locMax][ativMax];
    
    // TODO: Acrescentar um sistema de inventário para o jogo que seria
    //       a mochila do personagem, onde o mesmo poderia recolher objetos.

    /* Função Principal do Jogo
     *  A função principal se responsabiliza por fazer as inicializações 
     *  básicas do jogo, bem como executar os demais comandos que se
     *  relacionam com a aplicação como um todo.
     */
    public static void main(String[] args) {
        System.out.println("Jogo: Desafio do Nabo");

        inicializarLocais(); // Define os locais que o jogo terá.
        inicializarAtividades(); // Define as atividades que existiram no jogo.
        inicializarPersonagem(); 
        
        // Enquanto não chegarmos no fim do jogo, continuamos a receber os comandos
        // do usuário, suas ações, e atualizamos o mundo e seu status dentro do mesmo.
        while (rodando) {
            controleTemporal(); // Procedimento de controle do tempo no jogo.
            receberComando();   // Procedimento para receber e processar os comando no jogo.
            controleTermino();  // Procedimento para verificar se chegamos em uma condição
                                // onde devemos finalizar o jogo.
        }
    }

    /* Funções de inicialização do jogo.
     *  As funções de inicialização servem para
     *  definir as informações básicas do jogo.
     */
    public static void inicializarLocais() {
        // Adição dos principais locais da faculdade.
        locNome[0] = "Ponto do Fretado";
        locNome[1] = "Área Grande";
        locNome[2] = "Biblioteca";
        locNome[3] = "Alpha 1";
        locNome[4] = "Campo de Areia";
        locNome[5] = "Alpha 2";
        
        // Adição da ligação entre cada um dos locais no mapa.
        adicionarMovimentacao(0, 1); // Fretado - Área Grande
        adicionarMovimentacao(1, 2); // Área Grande - Biblioteca
        adicionarMovimentacao(1, 3); // Área Grande - Alpha 1
        adicionarMovimentacao(1, 4); // Área Grande - Campo de Areia
        adicionarMovimentacao(4, 5); // Campo de Areia - Alpha 2
    }
    
    // Inicialização das atividades.
    public static void inicializarAtividades() {
        // Local: Ponto do Fretado
        adicionarAtividade(0, "Pegar o fretado.", 8, 23);
        adicionarAtividade(0, "Pensar na vida.", 16 + rnd.nextInt(2), 23 + rnd.nextInt(1));

        // Local: Área Grande
        adicionarAtividade(1, "Conversar com os amigos.", 12, 16);

        // Local: Biblioteca
        adicionarAtividade(2, "Estudar para as provas.", 15 + rnd.nextInt(1), 19 + rnd.nextInt(3));
        adicionarAtividade(2, "Ler algum livro.", 15, 20);

        // Local: Alpha 1
        adicionarAtividade(3, "Ir para a aula.", tmpMinAula, tmpMedAula, true);
        adicionarAtividade(3, "Beber água do bebedouro.");

        // Local: Campo de Areia
        adicionarAtividade(4, "Passar o tempo.");
        adicionarAtividade(4, "Brincar no campo de areia.", 8, 16);

        // Local: Alpha 2
        adicionarAtividade(5, "Ir para a aula.", tmpMedAula, tmpMaxAula, true);
        adicionarAtividade(5, "Jogar Ping-Pong com um amigo.", 14, 21);
    }
    
    // Inicilização do personagem
    public static void inicializarPersonagem() {
        System.out.print("Qual é o seu nome: ");
        perNome = sc.nextLine();
        
        System.out.println();
    }
        
    /* Função com sobrecarga para o acrescimo das atividades no jogo.
     *  local:      Número da posição no vetor de locais do jogo.
     *  descricao:  Nome / descrição da nova atividade.
     *  horaIncio: Hoáario de início da atividade.
     *  horaFim:   Horário final da atividade.
     *  aulaOuNão: Se a atividade trata-se de uma aula ou não. 
     */
    public static void adicionarAtividade(int local, String descricao,
            double horaInicio, double horarioFim, boolean aulaOuNão)
    {
        int pos = ativQnt[local];

        ativNome[local][pos] = descricao;
        ativHora[local][pos][0] = horaInicio;
        ativHora[local][pos][1] = horarioFim;
        ativAula[local][pos] = aulaOuNão;

        ativQnt[local]++;
    }
    
    public static void adicionarAtividade(int local, String descricao,
            double horaInicio, double horarioFim)
    {
        int pos = ativQnt[local];

        ativNome[local][pos] = descricao;
        ativHora[local][pos][0] = horaInicio;
        ativHora[local][pos][1] = horarioFim;
        ativAula[local][pos] = false;

        ativQnt[local]++;
    }

    public static void adicionarAtividade(int loc, String nome) {
        int pos = ativQnt[loc];

        ativNome[loc][pos] = nome;          
        ativHora[loc][pos][0] = 1;          // Caso o horário não seja passado,
        ativHora[loc][pos][1] = tmpMaxHora; // assumimos que trata-se de um atividade
                                            // com horário integral.
        ativQnt[loc]++;
    }

    /* Função de controle da movimentação entre locais.
     *  posEntrada: Indica a posição do local de entrada no vetor de locais.
     *  posSaida:   Indica a posição do local de saída no vetor de locais.
     */
    public static void adicionarMovimentacao(int posEntrada, int posSaida) {
        locMov[posEntrada][posSaida] = true;
        locMov[posSaida][posEntrada] = true;
    }
    
    /* Função para impressão do local de acesso.
     *  pos: Posição no vetor de locais em que se deseja
     *       imprimir os locais de saída do mesmo.
     */
    public static void imprimirLocaisDeAcesso(int pos) {
        for (int num = 0; num < locMax; num++) {
            if (locMov[pos][num]) {
                System.out.println(" " + (num + 1) + ". " + locNome[num]);
            }
        }
    }
    /* Função que recolhe opção do usuário e move o
     * mesmo para o local desejado, caso seja possível.
     */
    public static void moverPersonagem() {
        System.out.println();

        System.out.print("Informe o número do local: ");
        int numLoc = sc.nextInt();

        numLoc = numLoc - 1;
        if (locMov[perPos][numLoc]) {
            perPos = numLoc;
        }
    }
    
    /* Procedimento para o recebimento da ação do jogador
     * no jogo, e posterior processamento da dada ação.
     */
    public static void receberComando() {
        int opcao = 0;
        System.out.println("O que você deseja fazer?");
        System.out.println(" 1. Verificar aulas do dia.");
        System.out.println(" 2. Verificar status.");
        System.out.println(" 3. Atividades no local.");
        System.out.println(" 4. Mover-se para outro local.");
        System.out.println(" 5. Esperar o tempo passar.");
        System.out.println(" 0. Fechar o jogo.");
        System.out.print("Informe a sua opção: ");

        opcao = sc.nextInt();
        if (opcao == 1) verificarAulas();
        if (opcao == 2) verificarStatus();
        if (opcao == 3) realizarAtividade();
        if (opcao == 4) moverParaLocal();
        if (opcao == 5) esperarTempo();
        if (opcao == 0) rodando = false;      
    }
    
    /* Procedimento para recolher o novo local onde o jogador deseja ir. */
    public static void moverParaLocal() {
        System.out.println();

        System.out.println("Você está em: " + locNome[perPos]);
        System.out.println("E pode se mover para: ");
        imprimirLocaisDeAcesso(perPos);

        System.out.print("Deseja mover-se? s / n: ");
        char opcao = sc.next().charAt(0);

        if (opcao == 's') {
            adicionarTempo(0.10);
            moverPersonagem();
        }

        System.out.println();
    }

    /* Função de controle do tempo no jogo.
     * Responsável por fazer a trasição dos dias.
     */
    public static void controleTemporal() {
        if (tmpHora > tmpMaxHora) {
            System.out.println("Um novo dia começou!");
            
            // Divisão das horas e dos minutos.
            int inteiro = (int) tmpHora;
            double decimal = tmpHora - inteiro;
        
            // Correção das horas.
             if(inteiro > 24) {
                 inteiro = 1;
                 decimal = (decimal * 100) / 60;
                 tmpHora = inteiro + decimal;
             }
            
            tmpHora = 1;
            tmpDia += 1;
            
            System.out.println();
        }
    }
    
    /* Função para verificar se o jogo terminou.
     * Imprimindo por fim o resultado das ações do jogador.
     */
    public static void controleTermino() {
        if (tmpDia > tmpMaxDia) {
            
            System.out.println("Você finalizou jogo " + perNome + "." + " Parabéns!");

            if (perEstu >= 10) {
                System.out.println(" Você foi um aluno exemplar.");
            } else if (perEstu <= 10 && perEstu >= 5) {
                System.out.println(" Você foi um aluno mediano.");
            } else {
                System.out.println(" Você foi um aluno péssimo.");
            }

            if (perFeli >= 10) {
                System.out.println(" Você foi um aluno muito feliz.");
            } else if (perFeli <= 10 && perFeli >= 5) {
                System.out.println(" Você foi um aluno meio feliz.");
            } else {
                System.out.println(" Você foi um aluno depressivo.");
            }

            rodando = false;
        }
    }

    /* Procedimento para retonar as aulas do dia. */
    public static void verificarAulas() {
        System.out.println();

        System.out.println("Você tem as seguintes aulas no dia: ");
        for (int loc = 0; loc < locMax; loc++) {
            for (int atv = 0; atv < ativMax; atv++) {
                if (ativAula[loc][atv]) {
                    System.out.print(" " + locNome[loc] + ": ");
                    System.out.println(" " + ativHora[loc][atv][0] + "h - " + ativHora[loc][atv][1] + "h");
                }
            }
        }

        System.out.println();
    }

    /* Procedimento para verificar quais são os status do jogador no jogo. */
    public static void verificarStatus() {
        System.out.println();

        System.out.println("Os seguintes status estão disponíveis: ");
        System.out.println(" Você está em: " +locNome[perPos]);
        System.out.printf("  Hora do dia: %.2f\n", tmpHora);
        System.out.println("  Dia atual: " + tmpDia);
        System.out.println(" Nível de felicidade: " + perFeli);
        System.out.println(" Nível de estudo: " + perEstu);

        System.out.println();
    }

    /* Procedimento para contabilizar a espera do jogador no jogo. */
    public static void esperarTempo() {
        System.out.println();

        System.out.println("Esperar por quanto tempo?");
        System.out.println(" 1. Dez minutos.");
        System.out.println(" 2. Meia hora.");
        System.out.println(" 3. Uma hora.");
        System.out.println(" 4. Três hora.");
        System.out.print("Informe sua opção: ");
        int opcao = sc.nextInt();

        if (opcao == 1) adicionarTempo(0.10);
        if (opcao == 2) adicionarTempo(0.30);
        if (opcao == 3) adicionarTempo(1);
        if (opcao == 4) adicionarTempo(3);
        
        System.out.println();
    }
    
    /* Função para incrementar um valor no horário atual do jogo. */
    public static void adicionarTempo(double hora) {
        tmpHora = tmpHora + hora;
        
        // Divisão das horas e dos minutos.
        int inteiro = (int) tmpHora;
        double decimal = tmpHora - inteiro;
        
        // Correção dos minutos.
        if(decimal > 60) {
            inteiro = inteiro + 1;
            decimal = (decimal * 100) / 60;
            tmpHora = inteiro + decimal;
        }
    }
    
    /* Procedimento para fazer as atividades em um local*/
    public static void realizarAtividade() {
        System.out.println();

        System.out.println("Você pode fazer as seguintes atividades no local: ");

        double horaInicio = -1;
        double horaFim = -1;
        boolean atividadeDisp = false;
        
        for (int num = 0; num < ativQnt[perPos]; num++) {
            horaInicio = ativHora[perPos][num][0];
            horaFim = ativHora[perPos][num][1];
            if (tmpHora >= horaInicio && tmpHora <= horaFim) {
                System.out.println(" " + (num + 1) + ". " + ativNome[perPos][num]);
                atividadeDisp = true;
            }
        }

        if (atividadeDisp) {
            System.out.print("Deseja fazer alguma atividade? s / n: ");
            char fazer = sc.next().charAt(0);
            
            System.out.println();
            
            if (fazer == 's') {
                System.out.print("Que atividade você deseja fazer: ");
                int qual = sc.nextInt();
                qual = qual - 1;
                
                System.out.println();
                calcularEfeitoDasAtividades(qual);
            } else {
                System.out.println("Atividade Indisponível.");
            }
        } else {
            System.out.println("Nenhuma atividade disponível.");
        }

        System.out.println();
    }

    /* Procedimento para calcular os efeitos das atividaes no jogo. */
    public static void calcularEfeitoDasAtividades(int opcao) {
        // Local: Ponto do Fretado
        if (perPos == 0) {
            if (opcao == 0) {
                System.out.println("Você foi para casa dormir.");
                System.out.println("Você recuperou seu nível de sono!");
                
                tmpHora = 1;
                tmpDia++;
            }

            if (opcao == 1) {
                System.out.println("Você teve uma crise existência de tanto pensar na vida.");
                System.out.println("Você diminuiu sua felicidade em 5 pontos.");
                adicionarTempo(0.30);
                perFeli -= 5;
            }
        }
        
        // Local: Área Grande
        if (perPos == 1) {
            if (opcao == 0) {
                System.out.println("Você foi conversar com um dos seus amigos.");
                System.out.println("Sua felicidade aumentou em 5 pontos.");
                adicionarTempo(0.30);
                perFeli += 5;
            }
        }
        
        // Local: Biblioteca
        if (perPos == 2) {
            if (opcao == 0) {
                System.out.println("Você foi estudar para as provas.");
                System.out.println("Você melhorou sua inteligência em 5 pontos.");
                adicionarTempo(0.30);
                perEstu += 5;
            }
             if (opcao == 1) {
                System.out.println("Você foi ler um livro.");
                System.out.println("Você melhorou sua inteligência em 3 pontos.");
                adicionarTempo(0.20);
                perEstu += 3;
            }
        }

        
        //Local: Alpha 1
        if (perPos == 3) {
            if (opcao == 0) {
                System.out.println("Você foi assitir aula.");
                System.out.println("Você melhorou sua inteligência em 10 pontos.");
                adicionarTempo(2);
                perEstu += 10;
            }
             if (opcao == 1) {
                System.out.println("Você foi beber água do bebedouro.");
                System.out.println("Você melhorou a sua felicidade em 3 pontos.");
                adicionarTempo(0.20);
                perFeli += 3;
            }
        }
        
        
        // Local: Campo de Areia
        if (perPos == 4) {
            if (opcao == 0) {
                if(tmpHora < 19){
                    System.out.println("Você passou o tempo olhando para o céu de nuvens.");
                } else if(tmpHora >= 19) {
                    System.out.println("Você passou o tempo olhando para o céu estrelado.");
                }
                
                System.out.println("Sua felicidade aumentou em 5 pontos.");
                adicionarTempo(0.3);
                perFeli += 5;
            }
            if (opcao == 1) {
                System.out.println("Você foi se divertir no campo de areia.");
                System.out.println("Sua felicidade aumentou em 10 pontos.");
                adicionarTempo(1);
                perEstu += 10;
            }
        }
        
        // Local: Alpha 2
        if (perPos == 5) {
            if (opcao == 0) {
                System.out.println("Você foi assitir aula.");
                System.out.println("Você melhorou sua inteligência em 10 pontos.");
                adicionarTempo(2);
                perEstu += 10;
            }
            if (opcao == 1) {
                System.out.println("Você foi jogar Ping-Pong com um amigo.");
                System.out.println("Sua felicidade aumentou 5 pontos.");
                adicionarTempo(0.3);
                perFeli += 5;
            }
        }
        
    }
}
