package markup;
import java.util.List;

public class Emphasis extends Marking {
    public Emphasis(List<MarkupElement> list) {
        super(list, "*", "<em>", "</em>");
    }
}
