package org.spl.common.structure;

import org.spl.common.ASTNode;
import org.spl.common.Nonterminal;
import org.spl.common.type.Type;
import org.spl.typechecker.exception.EmptyException;

import java.util.ArrayList;
import java.util.HashMap;

public class ScopeObject extends BindingObject {

    private ScopeObject m_parentScope;
    private ArrayList<ScopeObject> m_children;
    private ArrayList<BindingObject> m_declarationList;
    private FunctionObject m_functionObject;
    private ASTNode m_returnStatementNode;
    private Nonterminal m_nonterminal;

    private enum Occurrence {
        NOT_OCCURS, OCCURS_LOCALLY, OCCURS_GLOBALLY
    }

    // Special constructor for global scope
    public ScopeObject(ArrayList<BindingObject> declarationList) {
        super();
        m_parentScope = null;
        m_children = new ArrayList<ScopeObject>();
        m_declarationList = new ArrayList<BindingObject>(declarationList);
        m_functionObject = null;
        m_returnStatementNode = null;
        m_nonterminal = Nonterminal.SPL;

        for (BindingObject declaration : m_declarationList) {
            if (declaration instanceof FunctionObject) {
                ((FunctionObject) declaration).initialiseScopeObject(this);
            }
        }
    }

    public ScopeObject(ScopeObject parentScope, Nonterminal nonterminal) {
        super();
        m_parentScope = parentScope;
        m_children = new ArrayList<ScopeObject>();
        m_declarationList = new ArrayList<BindingObject>();
        m_functionObject = parentScope.getFunctionObject();
        m_returnStatementNode = null;
        m_nonterminal = nonterminal;

        parentScope.addChild(this);
    }

    public ScopeObject getParentScope() {
        return m_parentScope;
    }

    public ArrayList<ScopeObject> getChildren() {
        return m_children;
    }

    public ArrayList<BindingObject> getDeclarations() {
        return m_declarationList;
    }

    public FunctionObject getFunctionObject() {
        return m_functionObject;
    }

    public Nonterminal getNonterminal() {
        return m_nonterminal;
    }

    public void setFunctionObject(FunctionObject functionObject) {
        m_functionObject = functionObject;
    }

    public void setReturnStatementNode(ASTNode returnStatementNode) {
        m_returnStatementNode = returnStatementNode;
    }

    public boolean addDeclaration(BindingObject declaration) {
        if (verifyOccurrence(this, declaration) != Occurrence.OCCURS_LOCALLY) {
            m_declarationList.add(declaration);
            return true;
        }
        return false;
    }

    public void addChild(ScopeObject scopeObject) {
        m_children.add(scopeObject);
    }

    public boolean isGlobalScope() {
        return m_parentScope == null;
    }

    public boolean containsReturnStatement() {
        return m_returnStatementNode != null;
    }

    // Verifies if given declaration exists in this scope or its parents
    private Occurrence verifyOccurrence(ScopeObject scopeObject, BindingObject declaration) {
        if (scopeObject == null) {
            return Occurrence.NOT_OCCURS;
        }

        if (scopeObject.getDeclarations().contains(declaration)) {
            if (scopeObject == this) {
                return Occurrence.OCCURS_LOCALLY;
            } else {
                return Occurrence.OCCURS_GLOBALLY;
            }
        } else if (scopeObject.getFunctionObject() != null && scopeObject.getFunctionObject().getArguments().contains(declaration)) {
            return Occurrence.OCCURS_GLOBALLY;
        }

        return verifyOccurrence(scopeObject.getParentScope(), declaration);
    }

    private ArrayList<BindingObject> _findAllByIdentifier(String identifier, ScopeObject scopeObject) {
        if (scopeObject == null) {
            return new ArrayList<BindingObject>();
        }

        ArrayList<BindingObject> result = _findAllByIdentifier(identifier, scopeObject.getParentScope());

        if (scopeObject.getFunctionObject() != null) {
            for (BindingObject bindingObject : scopeObject.getFunctionObject().getArguments()) {
                if (bindingObject.getIdentifier().equals(identifier)) {
                    result.add(0, bindingObject);
                }
            }
        }

        for (BindingObject bindingObject : scopeObject.getDeclarations()) {
            if (bindingObject.getIdentifier().equals(identifier)) {
                result.add(0, bindingObject);
            }
        }

        return result;
    }

    public BindingObject findByIdentifier(String identifier) {
        ArrayList<BindingObject> result = _findAllByIdentifier(identifier, this);
        return result.size() > 0 ? result.get(0) : null;
    }

    // Tries to match function call to a defined function
    public FunctionObject match(FunctionCall functionCall) {
        ArrayList<BindingObject> objects = _findAllByIdentifier(functionCall.getIdentifier(), this);

        for (BindingObject object : objects) {
            if (object instanceof FunctionObject && ((FunctionObject) object).match(functionCall)) {
                return (FunctionObject) object;
            }
        }

        return null;
    }

    // Tries to match function call to a defined function
    public FunctionObject match(FunctionCall functionCall, HashMap<String, Type> polymorphicMap) throws EmptyException {
        ArrayList<BindingObject> objects = _findAllByIdentifier(functionCall.getIdentifier(), this);

        for (BindingObject object : objects) {
            if (object instanceof FunctionObject && ((FunctionObject) object).match(functionCall, polymorphicMap)) {
                return (FunctionObject) object;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "{declarations=" + m_declarationList.toString() +
                ", parent=" + m_parentScope + "}";
    }
}
