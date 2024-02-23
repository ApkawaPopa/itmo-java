package markup;

public class Text implements MarkupElement {
    String data;

    public Text(String s) {
        data = s;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(data);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(data);
    }
}
