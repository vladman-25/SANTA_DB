package result;

import java.util.ArrayList;

public final class ResultArray {
    private ArrayList<ResultChildren> children;

    public ArrayList<ResultChildren> getChildren() {
        return children;
    }

    public void setChildren(final ArrayList<ResultChildren> children) {
        this.children = children;
    }

    public ResultArray(final ArrayList<ResultChildren> children) {
        this.children = children;
    }
}
