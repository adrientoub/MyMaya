package script.types;

import java.util.List;

/**
 * Created by Adrien on 22/12/2016.
 */
public class ArgumentListType extends Type {
    private List<Type> types;

    public ArgumentListType(List<Type> types) {
        this.types = types;
    }

    public List<Type> getTypes() {
        return types;
    }

    @Override
    public boolean compatibleWith(Type other) {
        if (!(other instanceof ArgumentListType)) {
            return false;
        }
        ArgumentListType o = (ArgumentListType) other;
        if (types.size() != o.types.size()) {
            return false;
        }
        for (int i = 0; i < types.size(); i++) {
            if (!types.get(i).compatibleWith(o.types.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArgumentListType{" + "types=" + types + '}';
    }
}
