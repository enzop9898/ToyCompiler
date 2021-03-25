package Visitor;

import Node.*;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SyntaxVisitor implements Visitor<Element> {

    private Document xmlDocument;

    /**
     * Constructor and creator of XML-document
     */
    public SyntaxVisitor() {
        super();
        this.createDocument();
    }

    /**
     * Method to append the root of the tree
     * @param el is the root of the tree
     */
    public void appendRoot(Element el) {
        this.xmlDocument.appendChild(el);
    }

    /**
     * Method to create factory and builder for the XML-Document
     */
    public void createDocument() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            this.xmlDocument = docBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to print the XML
     */
    public void toXml() {
       TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(this.xmlDocument);
            StreamResult result = new StreamResult(new File("output.xml"));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public Document getXmlDocument() {
        return xmlDocument;
    }

    public void setXmlDocument(Document xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    @Override
    public Element visit(Program progOP) {
        Element el = this.xmlDocument.createElement(progOP.getNomeNode());

        if (progOP.getVardeclist() != null && progOP.getVardeclist().size() > 0) {
            Element eDecList = this.xmlDocument.createElement("VarDeclList");
            for(VarDecl d: progOP.getVardeclist()) {
                Element node = (Element) d.accept(this);
                eDecList.appendChild(node);
            }
            el.appendChild(eDecList);
        }


        if (progOP.getProclist() != null && progOP.getProclist().size() > 0) {
            Element eProcList = this.xmlDocument.createElement("ProcList");
            el.appendChild(eProcList);
            for(Proc p: progOP.getProclist()) {
                eProcList.appendChild(p.accept(this));
            }
        }

        return el;
    }

    @Override
    public Element visit(VarDecl vardeclOP) {
        Element el = this.xmlDocument.createElement((vardeclOP.getNomeNode()));
        el.appendChild(vardeclOP.getTipo().accept(this));

        if (vardeclOP.getIdlista() != null && vardeclOP.getIdlista().size() > 0) {
            for(IdInit id: vardeclOP.getIdlista()) {
                el.appendChild(id.accept(this));
            }
        }

        return el;
    }

    @Override
    public Element visit(Proc procOP) {
        Element el = this.xmlDocument.createElement((procOP.getNomeNode()));
        el.appendChild(procOP.getID().accept(this));
        if (procOP.getPardec() != null && procOP.getPardec().size() > 0) {
            Element parametri = this.xmlDocument.createElement("ParamsList");
            for(ParDecl pr: procOP.getPardec()) {
                parametri.appendChild(pr.accept(this));
            }
            el.appendChild(parametri);
        }

        if (procOP.getResdec() != null && procOP.getResdec().size() > 0) {
            Element listaResType = this.xmlDocument.createElement("ResultTypeList");
            for (Type t: procOP.getResdec()) {
                listaResType.appendChild(t.accept(this));
            }
            el.appendChild(listaResType);
        }

        if (procOP.getVardec() != null && procOP.getVardec().size() > 0) {
            Element listaVarDec = this.xmlDocument.createElement("VarDeclList");
            el.appendChild(listaVarDec);
            for (VarDecl vd: procOP.getVardec()) {
                listaVarDec.appendChild(vd.accept(this));
            }
        }

        if (procOP.getStatementlist() != null && procOP.getStatementlist().size() > 0) {
            Element el2 = this.xmlDocument.createElement("StatList");
            el.appendChild(el2);
            for (Stat t: procOP.getStatementlist()) {
                el2.appendChild(t.accept(this));
            }
        }

        if (procOP.getRetexpr() != null && procOP.getRetexpr().getListaExpr().size() > 0){
            Element el3 = this.xmlDocument.createElement("ReturnExpr");
            el.appendChild(el3);
            el3.appendChild(procOP.getRetexpr().accept(this));

        }

        return el;
    }

    @Override
    public Element visit(IfStat ifstatOP) {
        Element el = this.xmlDocument.createElement(ifstatOP.getNomeNode());
        Element condizioneif = this.xmlDocument.createElement("ConditionIF");
        el.appendChild(condizioneif);
        condizioneif.appendChild(ifstatOP.getEspressione().accept(this));
        if (ifstatOP.getListaStat() != null && ifstatOP.getListaStat().size() > 0) {
            Element bodyIF = this.xmlDocument.createElement("BodyIF");
            el.appendChild(bodyIF);
            for ( Stat s: ifstatOP.getListaStat()) {
                bodyIF.appendChild(s.accept(this));
            }
        }
        if (ifstatOP.getListaElif() != null && ifstatOP.getListaElif().size() > 0) {
            Element elifListIF = this.xmlDocument.createElement("ElifList");
            el.appendChild(elifListIF);
            for (Elif eli: ifstatOP.getListaElif()) {
                elifListIF.appendChild(eli.accept(this));
            }
        }
        if (ifstatOP.getEls() != null && ifstatOP.getEls().getStatList().size() > 0) {
            Element elseIF = this.xmlDocument.createElement("ElseBody");
            el.appendChild(elseIF);
            elseIF.appendChild(ifstatOP.getEls().accept(this));
        }
        return el;
    }

    @Override
    public Element visit(Type typeOP) {
        Element el = this.xmlDocument.createElement("TypeOP");
        el.setAttribute("type",typeOP.getNomeNode());
        return el;
    }

    @Override
    public Element visit(IdInit idinitOP) {
        Element el = this.xmlDocument.createElement(idinitOP.getNomeNode());
        el.appendChild(idinitOP.getId().accept(this));
        if ( idinitOP.getEspressione() != null) {
            el.appendChild(idinitOP.getEspressione().accept(this));
        }
        return el;
    }

    @Override
    public Element visit(ReturnExprs returnexprOP) {
        if ( returnexprOP != null) {
            Element el = this.xmlDocument.createElement(returnexprOP.getNomeNode());
            for ( Expr e: returnexprOP.getListaExpr()) {
                el.appendChild(e.accept(this));
            }
            return el;

        }
        return null;
    }

    @Override
    public Element visit(ParDecl pardeclOP) {
        Element el = this.xmlDocument.createElement(pardeclOP.getNomeNode());
        el.appendChild(pardeclOP.getTipo().accept(this));
        if (pardeclOP.getListaId() != null && pardeclOP.getListaId().size() > 0) {
            for (Identifier s: pardeclOP.getListaId()) {
                el.appendChild(s.accept(this));
            }
        }
        return el;
    }


    @Override
    public Element visit(ReadlnStat readlnOP) {
        Element el = this.xmlDocument.createElement(readlnOP.getNomeNode());

        if (readlnOP.getIdLista() != null && readlnOP.getIdLista().size() > 0) {
            Element listNode = this.xmlDocument.createElement("IdList");
            el.appendChild(listNode);
            for (Identifier p : readlnOP.getIdLista())
                listNode.appendChild(p.accept(this));
        }
        return el;
    }

    @Override
    public Element visit(WriteStat writestatOP) {
        Element el = this.xmlDocument.createElement(writestatOP.getNomeNode());
        for (Expr p : writestatOP.getExprLista()) {
            el.appendChild(p.accept(this));
        }
        return el;
    }

    @Override
    public Element visit(AssignStat assignstatOP) {
        Element el = this.xmlDocument.createElement(assignstatOP.getNomeNode());
        Element idList = this.xmlDocument.createElement("IdList");
        Element valList = this.xmlDocument.createElement("ValList");
        if (assignstatOP.getIdList() != null && assignstatOP.getIdList().size() > 0) {
            for (Identifier p : assignstatOP.getIdList()) {
                idList.appendChild(p.accept(this));
            }
            el.appendChild(idList);
        }
        if (assignstatOP.getExprList() != null && assignstatOP.getExprList().size() > 0) {
            for (Expr e: assignstatOP.getExprList()) {
                valList.appendChild(e.accept(this));
            }
            el.appendChild(valList);
        }
        return el;
    }

    @Override
    public Element visit(CallProc callprocOP) {
        Element el = this.xmlDocument.createElement(callprocOP.getNomeNode());
        el.appendChild(callprocOP.getId().accept(this));
        if (callprocOP.getExprList() != null && callprocOP.getExprList().size() > 0) {
            Element listap = this.xmlDocument.createElement("ParamList");
            el.appendChild(listap);
            for (Expr p : callprocOP.getExprList()) {
                listap.appendChild(p.accept(this));
            }
        }
        return el;
    }

    @Override
    public Element visit(WhileStat whilestatOP) {
        Element el = this.xmlDocument.createElement(whilestatOP.getNomeNode());
        if(whilestatOP.getStatList1()!=null){
        for (Stat p : whilestatOP.getStatList1()) {
            el.appendChild(p.accept(this));
        }}
        el.appendChild(whilestatOP.getEspressione().accept(this));
        if(whilestatOP.getStatList2()!=null) {
            for (Stat p1 : whilestatOP.getStatList2()) {
                el.appendChild(p1.accept(this));
            }
        }
        return el;
    }

    @Override
    public Element visit(Else elseOP) {
        if (elseOP != null) {

            Element el = this.xmlDocument.createElement(elseOP.getNomeNode());
            for (Stat p : elseOP.getStatList()) {
                el.appendChild(p.accept(this));
            }
            return el;

        }
        return null;
    }

    @Override
    public Element visit(IFEXPR efexpr) {
        Element el = this.xmlDocument.createElement(efexpr.getNomeNode());
        Element cond = this.xmlDocument.createElement("Condizione");
        el.appendChild(cond);
        cond.appendChild(efexpr.getCond().accept(this));
        Element seVera = this.xmlDocument.createElement("IfTrueExpr");
        el.appendChild(seVera);
        seVera.appendChild(efexpr.getSevera().accept(this));
        Element seFalsa = this.xmlDocument.createElement("IfFalseExpr");
        el.appendChild(seFalsa);
        seFalsa.appendChild(efexpr.getSefalsa().accept(this));
        return el;
    }

    @Override
    public Element visit(Elif elifOP) {
        Element el = this.xmlDocument.createElement(elifOP.getNomeNode());
        el.appendChild(elifOP.getEspressione().accept(this));
        if (elifOP.getListaStat() != null && elifOP.getListaStat().size() > 0) {
            Element statlist = this.xmlDocument.createElement("StatList");
            el.appendChild(statlist);
            for (Stat p : elifOP.getListaStat()) {
                statlist.appendChild(p.accept(this));
            }
        }

        return el;
    }

    @Override
    public Element visit(Int_const intconstOP) {
        Element el = this.xmlDocument.createElement(intconstOP.getNomeNode());
        el.setAttribute("value", String.valueOf(intconstOP.getVal()));
        return el;
    }

    @Override
    public Element visit(Float_const floatconstOP) {
        Element el = this.xmlDocument.createElement("FloatOP");
        el.appendChild(this.xmlDocument.createTextNode(String.valueOf(floatconstOP.getVal())));
        return el;
    }

    @Override
    public Element visit(String_const stringconstOP) {
        Element el = this.xmlDocument.createElement(stringconstOP.getNomeNode());
        el.setAttribute("value",stringconstOP.getVal());
        return el;
    }

    @Override
    public Element visit(Identifier id) {
        Element el = this.xmlDocument.createElement(id.getNomeNode());
        el.setAttribute("value",id.getVal());
        return el;
    }

    @Override
    public Element visit(PLUS plusOP) {
        Element el = this.xmlDocument.createElement(plusOP.getNomeNode());
        el.appendChild(plusOP.getRight().accept(this));
        el.appendChild(plusOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(MINUS minusOP) {
        Element el = this.xmlDocument.createElement(minusOP.getNomeNode());
        el.appendChild(minusOP.getRight().accept(this));
        el.appendChild(minusOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(TIMES timesOP) {
        Element el = this.xmlDocument.createElement(timesOP.getNomeNode());
        el.appendChild(timesOP.getRight().accept(this));
        el.appendChild(timesOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(DIV divOP) {
        Element el = this.xmlDocument.createElement(divOP.getNomeNode());
        el.appendChild(divOP.getRight().accept(this));
        el.appendChild(divOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(AND andOP) {
        Element el = this.xmlDocument.createElement(andOP.getNomeNode());
        el.appendChild(andOP.getRight().accept(this));
        el.appendChild(andOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(OR orOP) {
        Element el = this.xmlDocument.createElement(orOP.getNomeNode());
        el.appendChild(orOP.getRight().accept(this));
        el.appendChild(orOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(GT gtOP) {
        Element el = this.xmlDocument.createElement(gtOP.getNomeNode());
        el.appendChild(gtOP.getRight().accept(this));
        el.appendChild(gtOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(GE geOP) {
        Element el = this.xmlDocument.createElement(geOP.getNomeNode());
        el.appendChild(geOP.getRight().accept(this));
        el.appendChild(geOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(LT ltOP) {
        Element el = this.xmlDocument.createElement(ltOP.getNomeNode());
        el.appendChild(ltOP.getRight().accept(this));
        el.appendChild(ltOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(LE leOP) {
        Element el = this.xmlDocument.createElement(leOP.getNomeNode());
        el.appendChild(leOP.getRight().accept(this));
        el.appendChild(leOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(EQ eqOP) {
        Element el = this.xmlDocument.createElement(eqOP.getNomeNode());
        el.appendChild(eqOP.getRight().accept(this));
        el.appendChild(eqOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(NE neOP) {
        Element el = this.xmlDocument.createElement(neOP.getNomeNode());
        el.appendChild(neOP.getRight().accept(this));
        el.appendChild(neOP.getLeft().accept(this));
        return el;
    }

    @Override
    public Element visit(NOT notOP) {
        Element el = this.xmlDocument.createElement(notOP.getNomeNode());
        el.appendChild(notOP.getChild().accept(this));
        return el;
    }

    @Override
    public Element visit(UMINUS uminusOP) {
        Element el = this.xmlDocument.createElement(uminusOP.getNomeNode());
        el.appendChild(uminusOP.getChild().accept(this));
        return el;
    }

    @Override
    public Element visit(True trueOP) {
        Element el = this.xmlDocument.createElement(trueOP.getNomeNode());
        el.setAttribute("value", String.valueOf(trueOP.getValue()));
        return el;
    }

    @Override
    public Element visit(Null nullNode) {
        Element el = this.xmlDocument.createElement(nullNode.getNomeNode());
        return el;
    }



    @Override
    public Element visit(False falseOP) {
        Element el = this.xmlDocument.createElement(falseOP.getNomeNode());
        el.setAttribute("value", String.valueOf(falseOP.getValue()));
        return el;
    }
}
