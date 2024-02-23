package markup;

public interface MarkupElement {
    void toMarkdown(StringBuilder sb);
    void toHtml(StringBuilder sb);
}
