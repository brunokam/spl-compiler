package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.FunctionCall;
import org.spl.common.Nonterminal;
import org.spl.common.type.Type;
import org.spl.typechecker.exception.EmptyException;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope extends StructureObject {

    private static Integer currentId = 0;

    private Integer m_id;
    private Scope m_parentScope;
    private ArrayList<Scope> m_children;
    private ArrayList<StructureObject> m_structureList;
    private ArrayList<StructureObject> m_declarationList;
    private FunctionDeclaration m_functionDeclaration;
    private ASTNode m_returnStatementNode;
    private Nonterminal m_nonterminal;

    private enum Occurrence {
        NOT_OCCURS, OCCURS_LOCALLY, OCCURS_GLOBALLY
    }

    // Special constructor for global scope
    public Scope(ArrayList<StructureObject> declarationList) {
        super();
        m_id = currentId++;
        m_parentScope = null;
        m_children = new ArrayList<Scope>();
        m_structureList = new ArrayList<StructureObject>();
        m_declarationList = new ArrayList<StructureObject>(declarationList);
        m_functionDeclaration = null;
        m_returnStatementNode = null;
        m_nonterminal = Nonterminal.SPL;

        for (StructureObject declaration : m_declarationList) {
            if (declaration instanceof FunctionDeclaration) {
                ((FunctionDeclaration) declaration).initialiseScopeObject(this);
            }
        }
    }

    public Scope(Scope parentScope, Nonterminal nonterminal) {
        super();
        m_id = currentId++;
        m_parentScope = parentScope;
        m_children = new ArrayList<Scope>();
        m_structureList = new ArrayList<StructureObject>();
        m_declarationList = new ArrayList<StructureObject>();
        m_functionDeclaration = parentScope.getFunctionObject();
        m_returnStatementNode = null;
        m_nonterminal = nonterminal;

        parentScope.addChild(this);
    }

    public Scope getParentScope() {
        return m_parentScope;
    }

    public ArrayList<Scope> getChildren() {
        return m_children;
    }

    public ArrayList<StructureObject> getStructures() {
        return m_structureList;
    }

    public ArrayList<StructureObject> getDeclarations() {
        return m_declarationList;
    }

    public FunctionDeclaration getFunctionObject() {
        return m_functionDeclaration;
    }

    public Nonterminal getNonterminal() {
        return m_nonterminal;
    }

    public void setFunctionObject(FunctionDeclaration functionDeclaration) {
        m_functionDeclaration = functionDeclaration;
    }

    public void setReturnStatementNode(ASTNode returnStatementNode) {
        m_returnStatementNode = returnStatementNode;
    }

    public void addStructure(StructureObject structureObject) {
        m_structureList.add(structureObject);
    }

    public boolean addDeclaration(StructureObject declaration) {
        if (verifyOccurrence(this, declaration) != Occurrence.OCCURS_LOCALLY) {
            m_declarationList.add(declaration);
            return true;
        }
        return false;
    }

    public void addChild(Scope scope) {
        m_children.add(scope);
    }

    public boolean isGlobalScope() {
        return m_parentScope == null;
    }

    public boolean containsReturnStatement() {
        return m_returnStatementNode != null;
    }

    public Integer getId() {
        return m_id;
    }

    // Verifies if given declaration exists in this scope or its parents
    private Occurrence verifyOccurrence(Scope scope, StructureObject declaration) {
        if (scope == null) {
            return Occurrence.NOT_OCCURS;
        }

        if (scope.getDeclarations().contains(declaration)) {
            if (scope == this) {
                return Occurrence.OCCURS_LOCALLY;
            } else {
                return Occurrence.OCCURS_GLOBALLY;
            }
        } else if (scope.getFunctionObject() != null && scope.getFunctionObject().getArguments().contains(declaration)) {
            return Occurrence.OCCURS_GLOBALLY;
        }

        return verifyOccurrence(scope.getParentScope(), declaration);
    }

    private ArrayList<StructureObject> _findAllByIdentifier(String identifier, Scope scope) {
        if (scope == null) {
            return new ArrayList<StructureObject>();
        }

        ArrayList<StructureObject> result = _findAllByIdentifier(identifier, scope.getParentScope());

        if (scope.getFunctionObject() != null) {
            for (StructureObject structureObject : scope.getFunctionObject().getArguments()) {
                if (structureObject.getIdentifier().equals(identifier)) {
                    result.add(0, structureObject);
                }
            }
        }

        for (StructureObject structureObject : scope.getDeclarations()) {
            if (structureObject.getIdentifier().equals(identifier)) {
                result.add(0, structureObject);
            }
        }

        return result;
    }

    public StructureObject findByIdentifier(String identifier) {
        ArrayList<StructureObject> result = _findAllByIdentifier(identifier, this);
        return result.size() > 0 ? result.get(0) : null;
    }

    // Tries to match function call to a defined function
    public FunctionDeclaration match(FunctionCall functionCall) {
        ArrayList<StructureObject> objects = _findAllByIdentifier(functionCall.getIdentifier(), this);

        for (StructureObject object : objects) {
            if (object instanceof FunctionDeclaration && ((FunctionDeclaration) object).match(functionCall)) {
                return (FunctionDeclaration) object;
            }
        }

        return null;
    }

    // Tries to match function call to a defined function
    public FunctionDeclaration match(FunctionCall functionCall, HashMap<String, Type> polymorphicMap) throws EmptyException {
        ArrayList<StructureObject> objects = _findAllByIdentifier(functionCall.getIdentifier(), this);

        for (StructureObject object : objects) {
            if (object instanceof FunctionDeclaration && ((FunctionDeclaration) object).match(functionCall, polymorphicMap)) {
                return (FunctionDeclaration) object;
            }
        }

        return null;
    }

    @Override
    public void generate(Context context) {
    }

    @Override
    public String toString() {
        if (m_structureList.isEmpty()) {
            return "";
        }

        String str = m_nonterminal.toString() + ": " + m_structureList.toString() + "\n";

        for (Scope scope : m_children) {
            str += scope.toString();
        }

        return str;
    }
}
