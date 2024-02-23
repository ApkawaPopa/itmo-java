package markup;
import java.util.List;

public class Strikeout extends Marking {
    public Strikeout(List<MarkupElement> list) {
        super(list, "~", "<s>", "</s>");
    }
}
