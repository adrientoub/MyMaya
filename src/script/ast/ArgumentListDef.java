package script.ast;

import script.Visitor;
import script.types.AnyType;
import script.types.ArgumentListType;
import script.types.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Adrien on 22/12/2016.
 */
public class ArgumentListDef extends AstNode {
    private ArgumentListType argumentListType;
    private List<String> variableNames;

    public ArgumentListDef(List<String> variableNames) {
        this.variableNames = variableNames;
        List<Type> types = new ArrayList<>();
        for (String ignored: variableNames) {
            types.add(AnyType.getInstance());
        }
        this.argumentListType = new ArgumentListType(types);
    }

    public ArgumentListDef(String... variableNames) {
        this.variableNames = new ArrayList<>();
        Collections.addAll(this.variableNames, variableNames);
        List<Type> types = new ArrayList<>();
        for (String ignored: variableNames) {
            types.add(AnyType.getInstance());
        }
        this.argumentListType = new ArgumentListType(types);
    }

    public List<String> getVariableNames() {
        return variableNames;
    }

    @Override
    public Type getType() {
        return argumentListType;
    }

    @Override
    public void accept(Visitor v) {
    }

    public ArgumentListType getArgumentListType() {
        return argumentListType;
    }
}
