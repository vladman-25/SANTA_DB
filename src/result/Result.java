package result;

import java.util.ArrayList;

public final class Result {
    private ArrayList<ResultArray> annualChildren;

    public Result(final ArrayList<ResultArray> annualChildren) {
        this.annualChildren = annualChildren;
    }

    public ArrayList<ResultArray> getAnnualChildren() {
        return annualChildren;
    }

    public void setAnnualChildren(final ArrayList<ResultArray> annualChildren) {
        this.annualChildren = annualChildren;
    }
}
