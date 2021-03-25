import Node.Program;
import com.sun.org.apache.bcel.internal.classfile.Code;
import java_cup.runtime.*;

import java.io.*;
import Visitor.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import sun.plugin.com.Utils;

import static sun.plugin.com.Utils.*;

public class Main {

    public static void main(String[] args) throws Exception {

        File file=new File(args[0]);
        InputStream in = new FileInputStream(file);
        Reader reader = new InputStreamReader(in);
        parser p = new parser(new Yylex(reader));
        Symbol parseRes = p.debug_parse(); // l'uso di p.debug_parse() al posto di p.parse() produce tutte le azioni del parser durante il riconoscimento
        Program root = (Program) parseRes.value;
        System.out.println(parseRes.value);

        System.out.println("Starting to print");
        SyntaxVisitor sv = new SyntaxVisitor();
        sv.appendRoot((Element)sv.visit(root));
        sv.toXml();

        System.out.println("\nSEMANTIC VISITOR");
        SemanticVisitor smV = new SemanticVisitor();
        smV.visit(root);


        CodeGeneratorVisitor cv = new CodeGeneratorVisitor();
        String progName = args[0].replace("test_files","test_files/cgenfiles").replace(".txt", ".c");

        file = cv.conversion(progName,root);

        String compileFile =  args[0].replace("test_files/", "").replace(".txt", "");

        System.out.println("nome file: "+ compileFile);

        Runtime.getRuntime().exec("cmd.exe /c clang -pthread -lmsvcmrt -o " + compileFile + ".exe " + compileFile + ".c", null, new File("test_files\\cgenfiles\\"));


    }

}
