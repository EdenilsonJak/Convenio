package com.ejsistemas.lima.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omnifaces.util.Faces;

import com.ejsistemas.lima.util.jsf.FacesUtil;
/**
 * Classe com métodos para executar o dump do MySQL.
 * O "bin" do MySQL deve estar no PATH do SO.
 *
 * @author David Buzatto
 */
public class MysqlDumputils {
    /**
     * Exporta para um arquivo os dados de uma base de dados específica.
     *
     * @param arquivo Caminho do arquivo que deve ser gerado com a
     * saída da exportação.
     * @param usuario Usuário usado para conectar na base.
     * @param senha Senha do usuário.
     * @param base Nome da base a ser exportada.
     * @param tabelas Tabelas da base especifica a serem exportadas. Caso não
     * seja expecificada nenhuma, todas as tabelas serão exportadas.
     * @throws IOException
     */
	public static void dump(
            String arquivo,
            String usuario,
            String senha,
            String base,
            String... tabelas ) throws IOException {
        // prepara o comando
		Date data = new Date();
		String dataFormatada = new SimpleDateFormat("dd-MM-yyyy").format(data);
		
		StringBuilder sbComando = new StringBuilder( "mysqldump" );
        sbComando.append( " -u " );
        sbComando.append( usuario );
        /*Se o valor da senha for diferente de vazio, 
        adiciona o parametro de senha, caso contrário não adiciona, 
        pois pode gerar um arquivo vazio
        */
        if(senha != ""){
        // o valor do parâmetro -p não tem espaço!
        sbComando.append( " -p" );
        sbComando.append( senha );
        }
        sbComando.append(" -e");
        sbComando.append(" -x");
        sbComando.append( " " );
        sbComando.append( base );
        for ( String tabela : tabelas ) {
            sbComando.append( " " );
            sbComando.append( tabela );
        }
        // Tempo
        long t1, t2, tempo;
        FacesUtil.addInfoMessage( "Processo de Backup Iniciado...");
        // tempo inicio
        t1 = System.currentTimeMillis();
        // executa o comando
        Process processo = Runtime.getRuntime().exec(sbComando.toString());
        // cria os stream gobblers, um para o stream de entrada e um para o
        // stream de erro. os gobblers vão consumir os streams do processo
        StreamGobbler sgInput = new StreamGobbler(processo.getInputStream(), "input", new File( arquivo ) );
        StreamGobbler sgError = new StreamGobbler(processo.getErrorStream(), "error" );
        // cria uma thread para cada stream gobbler e as inicia
        new Thread( sgInput ).start();
        new Thread( sgError ).start();
        // tempo fim
        t2 = System.currentTimeMillis();
        // Tempo total da operação
        tempo = t2 - t1;
        FacesUtil.addInfoMessage("Processo de Backup Executado em: "+ tempo + "ms");
        FacesUtil.addInfoMessage("Processo de Backup Finalizado! arquivo encontra-se em C:\\bd_santarosa\\bd_santarosa_"+dataFormatada+".sql");
    }
    /**
     * Exporta para um arquivo os dados de todas as bases de dados.
     *
     * @param arquivo Caminho do arquivo que deve ser gerado com a
     * saída da exportação.
     * @param usuario Usuário usado para conectar na base.
     * @param senha Senha do usuário.
     * @throws IOException
     */
    public static void dumpAll(
            String arquivo,
            String usuario,
            String senha ) throws IOException {
        dump( arquivo, usuario, senha, "--all-databases" );
    }
    /**
     * Restaura os dados de um script sql para base de dados.
     *
     * @param arquivo Caminho do arquivo que será restaurado
     * @param host Servidor onde se encontra a base de dados.
     * @param porta Porta utilizada para conectar com o host.
     * @param usuario Usuário usado para conectar na base.
     * @param senha Senha do usuário.
     * @param base Nome da base a ser restaurada.
     * @throws IOException
     */
    public static void restore(String arquivo,
            String host,
            String porta,
            String usuario,
            String senha,
            String base) throws IOException {
        // prepara o comando
        StringBuilder sbComando = new StringBuilder( "cmd /c mysql" );
        sbComando.append( " -h" );
        sbComando.append( host );
        if(!porta.equals("")){
        sbComando.append( " -P " );
        sbComando.append( porta );
        }
        sbComando.append( " -u" );
        sbComando.append( usuario );
        /*Se o valor da senha for diferente de vazio, 
        adiciona o parametro de senha, caso contrário não adiciona, 
        pois pode gerar um arquivo vazio
        */
        if(!senha.equals("")){
        // o valor do parâmetro -p não tem espaço!
        sbComando.append( " -p" );
        sbComando.append( senha );
        }
        sbComando.append( " " );
        sbComando.append( base );
        sbComando.append( " < " );
        sbComando.append( arquivo );
        // Tempo
        long t1, t2, tempo;
        FacesUtil.addInfoMessage("Processo de Restauração Iniciado...");
        //imprime o comando
        //System.out.println("Comando: " + sbComando.toString());
        //tempo inicio
        t1 = System.currentTimeMillis();
        // executa o comando
        Process processo = Runtime.getRuntime().exec(sbComando.toString());
        // cria os stream gobblers, um para o stream de entrada e um para o
        StreamGobbler sgError = new StreamGobbler(processo.getErrorStream(), "error" );
        // cria uma thread para cada stream gobbler e as inicia
        new Thread( sgError ).start();
        // tempo fim
        t2 = System.currentTimeMillis();
        // Tempo total da operação
        tempo = t2 - t1;
        FacesUtil.addInfoMessage("Processo de Restauração Executado em: "+ tempo + "ms");
        FacesUtil.addInfoMessage("Processo de Restauração Finalizado. De arquivo "+arquivo );
    }
//    public static void main( String[] args ) throws Exception {
//        // exporta as tabelas "tabelaX" e "tabelaY" da base "minha_base" para o
//        // arquivo "C:/tabelaXeYMinhaBase.sql"
//        //dump( "C:/tabelaXeYMinhaBase.sql", "root", "root", "minha_base", "tabelaX", "tabelaY" );
//        // exporta a base "minha_base" para o arquivo "C:/minhaBase.sql"
//        dump("C:/bakup/teste.sql", "root", "", "db_teste");
//    	restore("C:/bakup/teste.sql", "localhost", "3306", "root", "", "db_restore");
//        // exporta todas as bases de dados para o arquivo "C:/todasAsBases.sql"
//        //dumpAll( "C:/todasAsBases.sql", "root", "root" );
//    }
}
/**
 * Thread para consumir os streams de processos.
 * Baseada na implementação apresentada em:
 * http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?page=1
 *
 * @author David Buzatto
 */
class StreamGobbler implements Runnable {
    private InputStream is;
    private String type;
    private FileWriter fw;
    public StreamGobbler( InputStream is, String type ) {
        this.is = is;
        this.type = type;
    }
    public StreamGobbler( InputStream is, String type, File file ){
        try {
        	this.is = is;
            this.type = type;
            this.fw = new FileWriter( file );
		} catch (IOException e) {
			FacesUtil.addAlerMessage(e.getMessage());
		}
    }
    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader( is );
            BufferedReader br = new BufferedReader( isr );
            String line = null;
            while ( ( line = br.readLine() ) != null ) {
                if ( fw != null ) {
                    fw.write( line + "\n" );
                } else {
                    System.out.println( type + ">" + line );
                }
            }
            if ( fw != null ) {
                fw.close();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}