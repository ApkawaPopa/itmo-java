package markup;
import java.util.*;

public class Marking implements MarkupElement {
    List<MarkupElement> data;
    String tag, openHtml, closeHtml;

    protected Marking(List<MarkupElement> data, String tag, String openHtml, String closeHtml) {
        this.data = data;
        this.tag = tag;
        this.openHtml = openHtml;
        this.closeHtml = closeHtml;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(tag);
        for (MarkupElement e : data) {
            e.toMarkdown(sb);
        }
        sb.append(tag);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(openHtml);
        for (MarkupElement e : data) {
            e.toHtml(sb);
        }
        sb.append(closeHtml);
    }
}
