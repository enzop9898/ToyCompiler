# ToyCompiler
a simple compiler of the toy language (for which the pdf documentation is present) complete with lexical, syntactic and semantic analysis. Finally the Intermediate code in C was created and the .exe was produced
DOCUMENTAZIONE TOY2C

Analisi Lessicale e Sintattica
Il primo modulo progettuale è stato quello di generare un lexer, usando JFLEX, in
grado di trasformare un codice sorgente toy in uno stream di token.
-JFlex generator: C:\JFLEX\bin\jflex -d src srcjflexcup\toy.flex
Successivamente, si è passati alla generazione di un analizzatore sintattico, il quale
ha il compito di validare sintatticamente il flusso di token generato dal lexer.
Questo modulo è stato generato attraverso Java CUP. La grammatica S-Attribuita
inserita all'interno di CUP non è stata modificata e i conflitti sono stati risolti
attraverso l'ausilio delle precedenze.
-CUP generator: java -jar C:\CUP\java-cup-11b.jar -dump -destdir src
srcjflexcup/toy.cup 2> dumpfile.txt
SINTAX VISITOR:
È stato realizzato un primo visitor sintattico per la generazione dell'AST stampato in
un file XML.
Il file XML è stato generato con l'ausilio delle librerie;
org.w3c.dom.Document;
org.w3c.dom.Element;
javax.xml;
L'albero sintattico generato viene scritto in un file "output.xml".
SEMANTIC VISITOR:
È stato realizzato un secondo visitor atto a effettuare i controlli semantici.
Tipi di base verificati
Γ ⊢ 𝑛𝑢𝑙𝑙 ∶ 𝑁𝑢𝑙𝑙
Γ ⊢ 𝑡𝑟𝑢𝑒 ∶ 𝐵𝑜𝑜𝑙𝑒𝑎𝑛
Γ ⊢ 𝑓𝑎𝑙𝑠𝑒 ∶ 𝐵𝑜𝑜𝑙𝑒𝑎𝑛
Γ ⊢ 𝑖𝑛𝑡 ∶ 𝐼𝑛𝑡𝑒𝑔𝑒𝑟
Γ ⊢ 𝑓𝑙𝑜𝑎𝑡 ∶ 𝐹𝑙𝑜𝑎𝑡
Γ ⊢ 𝑠𝑡𝑟𝑖𝑛𝑔 ∶ 𝑆𝑡𝑟𝑖𝑛g
REGOLE DI INFERENZA:
Controllo di tipo per l’ID
(x ∶ τ)
∈ Γ Γ ⊢
𝑥 ∶ τ
Controllo di tipo per l’inizializzazione per l’ID
𝛤 ⊢ 𝑥 ∶ 𝜏 𝐴𝑁𝐷 𝑒𝑥𝑝𝑟 ∶ 𝜏′𝐴𝑁𝐷 controllaCompatTipi(𝜏,
𝜏′): 𝜏
𝛤 ⊢ (𝑥 ∶= 𝑒𝑥𝑝𝑟):𝜏
Controllo di tipo per l’operazione unaria
Γ ⊢ 𝑎𝑟𝑔 ∶ τ1 𝐴𝑁𝐷 𝑔𝑒𝑡𝑇𝑦𝑝𝑒𝑆𝑖𝑛𝑔𝑙𝑒(𝑜𝑝, τ1
) =
τ1 Γ ⊢ (𝑜𝑝 𝑎𝑟𝑔) ∶ τ1
Controllo di tipo per le operazioni binarie
Γ ⊢ 𝑎𝑟𝑔1 ∶ τ1 𝐴𝑁𝐷 Γ ⊢ 𝑎𝑟𝑔2 ∶ τ2 𝐴𝑁𝐷 𝑔𝑒𝑡𝑇𝑦𝑝𝑒𝑂𝑝𝑒𝑟𝑎𝑡𝑖𝑜𝑛𝑠(𝑜𝑝, τ1, τ2
)
= τ Γ ⊢ (𝑎𝑟𝑔1 𝑜𝑝 𝑎𝑟𝑔2
) ∶ τ
Controlli per gli statement
Alcuni attributi sono caratterizzati dal canNull, il quale sta a specificare che esso può
annullarsi, ergo risulta un attributo opzionale e la sua assenza non implica la
scorrettezza della regola semantica.
Con […] intendiamo gli attributi opzionali; con {…} intendiamo una lista.
If statement semantic rule
Γ ⊢ 𝑐: 𝐵𝑜𝑜𝑙𝑒𝑎𝑛 𝐴𝑁𝐷 Γ ⊢ 𝑖𝑓Lista𝑆𝑡𝑎𝑡 𝐴𝑁𝐷 Γ ⊢ 𝑒𝑙𝑖𝑓𝐿𝑖𝑠𝑡 𝒄𝒂𝒏𝑵𝒖𝒍𝒍 𝐴𝑁𝐷 Γ ⊢ E𝑙𝑠𝑒
𝒄𝒂𝒏𝑵𝒖𝒍𝒍 Γ ⊢ 𝑖𝑓 𝑐 𝑡ℎ𝑒𝑛 𝑖𝑓Lista𝑆𝑡𝑎𝑡 [𝑒𝑙𝑖𝑓𝐿𝑖𝑠𝑡] [Else] 𝑓𝑖
Elif statement semantic rule
While statement semantic rule
Γ ⊢ ListaStat1 𝒄𝒂𝒏𝑵𝒖𝒍𝒍 𝐴𝑁𝐷 Γ ⊢ 𝑐𝑜𝑛𝑑: 𝐵𝑜𝑜𝑙𝑒𝑎𝑛 𝐴𝑁𝐷 Γ ⊢ ListaStat2
Γ ⊢ 𝑤ℎ𝑖𝑙𝑒 [ListaStat1] → 𝑐𝑜𝑛𝑑 𝑑𝑜 ListaStat2 𝑜𝑑
Readln statement semantic rule
Write statement semantic rule
Assign statement semantic rule
Call procedure statement semantic rule
Γ ⊢ 𝑐: 𝐵𝑜𝑜𝑙𝑒𝑎𝑛 𝐴𝑁𝐷 Γ ⊢ StatList
Γ ⊢ 𝑒𝑙𝑖𝑓 𝑐 𝑡ℎ𝑒𝑛 StatList
Γ ⊢ 𝑖𝑑𝐿𝑖𝑠𝑡: {τ1, … , τn}
Γ ⊢ 𝑟𝑒𝑎𝑑𝑙𝑛(𝑖𝑑𝐿𝑖𝑠𝑡)
Γ ⊢ 𝑒𝑥𝑝𝑟𝐿𝑖𝑠𝑡: {τ1, … , τn}
Γ ⊢ 𝑤𝑟𝑖𝑡𝑒(𝑒𝑥𝑝𝑟𝐿𝑖𝑠𝑡)
Γ ⊢ 𝑖𝑑𝐿𝑖𝑠𝑡: {τ1, … , τn
} 𝐴𝑁𝐷 Γ ⊢ 𝑒𝑥𝑝𝑟𝐿𝑖𝑠𝑡: {τ1, … , τn}
Γ ⊢ 𝑖𝑑𝐿𝑖𝑠𝑡 ∶= 𝑒𝑥𝑝𝑟𝐿𝑖𝑠𝑡
Γ ⊢ 𝑖𝑑: {τ1, … , τn
} 𝐴𝑁𝐷 Γ ⊢ 𝑒𝑥𝑝𝑟𝐿𝑖𝑠𝑡: {τ1, … , τn
} 𝒄𝒂𝒏𝑵𝒖𝒍𝒍
Γ ⊢ 𝑖𝑑([𝑒𝑥𝑝𝑟𝐿𝑖𝑠𝑡])
Le liste per espressioni, inizializzazioni, restituzioni, variabili e procedure sono date
vere se e solo se i controlli di tipo per gli elementi interni risultano essere corretti.
𝑔𝑒𝑡𝑇𝑦𝑝𝑒𝑂𝑝𝑒𝑟𝑎𝑡𝑖𝑜𝑛𝑠 (𝑜𝑝, τ1, τ2
)
op t1 t2 result
+ - * / Integer Integer Integer
+ - * / Integer Float Float
+ - * / Float Integer Float
+ - * / Float Float Float
+ String String String
= < > <= >= <> Boolean Boolean Boolean
= < > <= >= <> Integer Integer Boolean
= < > <= >= <> Integer Float Boolean
= < > <= >= <> Float Integer Boolean
= < > <= >= <> Float Float Boolean
= < > <= >= <> String String Boolean
AND OR Boolean Boolean Boolean
Compatibilità di tipi (per assegnazioni, passaggio di parametri per Callproc() e tipi di ritorno):
controllaCompatibilità (t1, t2)
t1 t2 result
Integer Integer true
Float Float true
Float Integer true
Integer Float true
String String True
Boolean Boolean True
Altro riguardo l’analisi semantica
Per la validità del programma, è stato inserito anche un controllo per verificare la
presenza della funzione main e che assicuri che sia unica, proprio come ogni altra
funzione. Il main si differisce dalle altre funzioni per il fatto che è dichiarabile
ovunque nel programma, a prescindere dalle dipendenze delle altre funzioni.
Inoltre, non avendo limitazioni dettate dalla traccia circa il tipo di ritorno del main e
dei suoi parametri di ingresso,si è deciso di limitare questi aspetti alsingolo tipo void
e al non utilizzo dei parametri in ingresso, in modo tale da evitare ambiguità
dell’utente con interazioni non effettuabili se non con strumenti diversi rispetto al
semplice editing su file di testo, al fine di creare un eseguibile toy funzionante.
Infine, nella tabella dei tipi inerente alle operazioni binarie è stata specificata
un’operazione in cui vengono addizionate due variabili di tipo String: con tale
operazione si intende la concatenazione tra stringhe, la quale restituisce,
ovviamente, una nuova variabile di tipo String.
GENERAZIONE CODICE INTERMEDIO:
Di seguito vengono riportati tutti gli accorgimenti implementativi avuti, al fine
tradurre il sorgente toy in codice C (codice intermedio target, stabilito dalla traccia).
• Introdotte le librerie stdio.h, stdlib.h, stdbool.h e string.h per il corretto
funzionamento del codice che verrà generato;
• Al fine di implementare i ritorni multipli, si è deciso di utilizzare il concetto di
struttura wrapper per racchiudere in un unico record tutti gli attributi di ritorno
di una funzione. A tal punto, per ogni funzione presente nel sorgente diversa dal
main e con tipo di ritorno diverso da void, viene generata una struttura
contenente come parametri tutti i tipi di ritorno dichiarati nel sorgente Toy.
• Il tipo stringa viene implementato tramite un array di chat con dimensione oari a
512;
• Al fine di effettuare una corretta concatenazione di stringhe, in ogni sorgente C
generato verrà iniettata una funzione di servizio che, tramite l’uso di malloc e
concat (funzione di string.h), permetterà di effettuare tale operazione
correttamente;
• I confronti di tipo booleano tra stringe vengono tradotti in C tramite l’ausilio
della funzione di libreria strcmp della libreria string.h;
• Il linguaggio C non supporta nativamente i booleani; sono stati introdotti con una
libreria, ma il fatto che non vengano supportati nativamente ha creato qualche
problema con la funzione scanf: siccome non permette la gestione dei booleani,
è stato necessario permettere la generazione di una variabile intera temporanea
ogni qualvolta che si utilizzi il booleano in tale funzione. Il valore della temporanea
equivarrà al rispettivo valore booleano;
